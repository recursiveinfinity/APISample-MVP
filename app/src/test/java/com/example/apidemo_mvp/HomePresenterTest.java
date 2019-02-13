package com.example.apidemo_mvp;

import android.accounts.NetworkErrorException;
import com.example.apidemo_mvp.model.ISSResponse;
import com.example.apidemo_mvp.model.Response;
import com.example.apidemo_mvp.network.ISSService;
import com.example.ui.home.HomeContract;
import com.example.ui.home.HomePresenter;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

@RunWith(MockitoJUnitRunner.class)
public class HomePresenterTest {

    @Mock
    private ISSService issService;

    @Mock
    private HomeContract.View view;

    @Captor
    private ArgumentCaptor<List<Response>> responseCaptor;

    private ISSResponse issResponse;

    private HomePresenter homePresenter;


    @BeforeClass
    public static void setupMainThread() {
        Scheduler scheduler = new Scheduler() {
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run,
                        true);
            }
        };
        RxJavaPlugins.setInitIoSchedulerHandler(schedulerCallable -> scheduler);
        RxAndroidPlugins.
                setInitMainThreadSchedulerHandler(schedulerCallable -> scheduler);
    }

    @Before
    public void setup() {
        homePresenter = new HomePresenter(issService, view);

    }

    @Test
    public void testGetISSPasses_Success() {
        //Given
        List<Response> responseList = new ArrayList<>();
        Response response = new Response(10, 56L);
        responseList.add(response);
        issResponse = new ISSResponse("", null, responseList);
        Mockito.when(issService.getISSPasses(10.0, 90.0))
                .thenReturn(Observable.just(issResponse));
        //When
        homePresenter.getISSPasses("10", "90");
        //Then
        Mockito.verify(view).showResults(responseCaptor.capture());
        Assert.assertEquals(1, responseCaptor.getValue().size());
        Response firstValue = responseCaptor.getValue().get(0);
        Assert.assertEquals(100, firstValue.getDuration());
        Assert.assertEquals(100L, firstValue.getRisetime());
    }

    @Test
    public void testGetISSPasses_Failure_Socket() {
        //Given
        Mockito.when(issService.getISSPasses(10.0, 90.0))
                .thenReturn(Observable.error(new SocketException("Test Error")));
        //when
        homePresenter.getISSPasses("10", "90");

        //Then
        Mockito.verify(view).showError("Socket Error");
    }


}

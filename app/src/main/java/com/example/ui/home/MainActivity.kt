package com.example.ui.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.test.espresso.idling.CountingIdlingResource
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.App
import com.example.apidemo_mvp.R
import com.example.apidemo_mvp.model.Response
import com.example.apidemo_mvp.network.ISSService
import com.example.di.DaggerAppComponent
import com.example.di.OkHttpWithoutLogging
import com.example.ui.home.di.DaggerHomeComponent
import com.example.ui.home.di.HomeModule
import kotlinx.android.synthetic.main.activity_iss.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.IllegalArgumentException
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HomeContract.View {

    private val idlingResource = CountingIdlingResource("RxJavaResource")

    override fun showPictures() {

    }

    private val issAdapter = ISSAdapter()

    @Inject
    lateinit var homePresenter: HomeContract.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iss)


        DaggerHomeComponent.builder()
            .appComponent((application as App).getComponent())
            .homeModule(HomeModule(this))
            .build()
            .inject(this)

        rvPasses.layoutManager = LinearLayoutManager(this)
        rvPasses.adapter = issAdapter

        btnSubmit.setOnClickListener {
            idlingResource.increment()
            homePresenter.getISSPasses(
                etLatitude.text.toString(),
                etLongitude.text.toString()
            )
        }


    }

    override fun showResults(results: List<Response>) {
        issAdapter.setData(results)
        idlingResource.decrement()
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun getIdlingResource() = idlingResource


}

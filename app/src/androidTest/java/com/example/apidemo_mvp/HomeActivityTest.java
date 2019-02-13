package com.example.apidemo_mvp;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.ui.home.MainActivity;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {

    private static String FILE_NAME = "200_response.json";

    private ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class, true, false);

    private MockWebServer mockWebServer;

    @Before
    public void setup() {
        mockWebServer = new MockWebServer();

        try {
            mockWebServer.start();
            mockWebServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setBody(TestHelper.getStringFromJsonFile(
                    InstrumentationRegistry.getContext(), FILE_NAME)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Constants
                .Companion.setBASE_URL_ISS(mockWebServer
                .url("/").toString());
        activityTestRule.launchActivity(new Intent());
    }

    @Test
    public void testData_Success() {
        CountingIdlingResource countingIdlingResource =
                activityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(countingIdlingResource);

        Espresso.onView(ViewMatchers.withId(R.id.etLatitude)).perform(
                ViewActions.typeText("10"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.etLongitude)).perform(
                ViewActions.typeText("10"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.btnSubmit)).perform(
                ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.rvPasses)).check(
                ViewAssertions.matches(atPosition(0,
                        ViewMatchers.hasDescendant(ViewMatchers.withText("0")))));
    }


    public static Matcher<View> atPosition(int position, final Matcher<View> matcher) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("The item is not found");
                matcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(RecyclerView recyclerView) {
                RecyclerView.ViewHolder viewHolder =
                        recyclerView.findViewHolderForAdapterPosition(position);

                return viewHolder != null &&
                        matcher.matches(viewHolder.itemView);
            }
        };
    }
}

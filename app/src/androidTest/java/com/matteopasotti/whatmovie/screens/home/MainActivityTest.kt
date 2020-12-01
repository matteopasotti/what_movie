package com.matteopasotti.whatmovie.screens.home

import android.os.SystemClock
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.base.BaseUITest
import com.matteopasotti.whatmovie.di.generateTestAppComponent
import com.matteopasotti.whatmovie.helpers.recyclerItemAtPosition
import com.matteopasotti.whatmovie.view.MainActivity
import com.matteopasotti.whatmovie.view.ui.movie_detail.MovieDetailActivity
import com.matteopasotti.whatmovie.view.viewholder.MovieHomeViewHolderNormal
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4::class)
class MainActivityTest : BaseUITest() {

    private val titleTestFirstItem = "Bronx"

    private val overviewTestFirstItem = "Caught in the crosshairs of police corruption and " +
            "Marseille’s warring gangs, a loyal cop must protect his squad by taking matters" +
            " into his own hands."

    private val titleTestSecondItem = "Enola Holmes"

    @Before
    fun start(){
        super.setUp()

        Intents.init()

        if(GlobalContext.getOrNull() == null) {
            startKoin {
                androidContext(ApplicationProvider.getApplicationContext())
                modules( generateTestAppComponent(getMockWebServerUrl()))
            }
        } else {
            loadKoinModules(generateTestAppComponent(getMockWebServerUrl()).toMutableList())
        }

    }

    @After
    fun cleanUp(){
        super.tearDown()

        Intents.release()
    }

    @Test
    fun test_recyclerview_elements_for_expected_response() {
        val scenario = launchActivity<MainActivity>()

        mockNetworkResponseWithFileContent("success_popular_movie_response.json", HttpURLConnection.HTTP_OK)

        //Wait for MockWebServer to get back with response
        SystemClock.sleep(1000)

        //Check if item at 0th position is having 0th element in json
        Espresso.onView(withId(R.id.movie_list))
            .check(
                ViewAssertions.matches(
                    recyclerItemAtPosition(
                        0,
                        ViewMatchers.hasDescendant(ViewMatchers.withText(titleTestFirstItem))
                    )
                )
            )

        Espresso.onView(withId(R.id.movie_list))
            .check(
                ViewAssertions.matches(
                    recyclerItemAtPosition(
                        0,
                        ViewMatchers.hasDescendant(ViewMatchers.withText(overviewTestFirstItem))
                    )
                )
            )

        //Scroll to 9th index in json
        Espresso.onView(withId(R.id.movie_list)).perform(
            RecyclerViewActions.scrollToPosition<MovieHomeViewHolderNormal>(9))

        //Check if item at 9th position is having 9th element in json
        Espresso.onView(withId(R.id.movie_list))
            .check(matches(recyclerItemAtPosition(9, ViewMatchers.hasDescendant(
                ViewMatchers.withText(
                    titleTestSecondItem
                )
            ))))
    }

    @Test
    fun test_loader_is_shown_and_hidden_when_we_got_data() {
        val scenario = launchActivity<MainActivity>()

        Espresso.onView(withId(R.id.progress)).check(matches(isDisplayed()))

        mockNetworkResponseWithFileContent("success_popular_movie_response.json", HttpURLConnection.HTTP_OK)

        //Wait for MockWebServer to get back with response
        SystemClock.sleep(1000)

        Espresso.onView(withId(R.id.progress)).check(matches(not(isDisplayed())))
    }

    @Test
    fun test_click_on_movie_open_moviedetailactivity(){
        val scenario = launchActivity<MainActivity>()

        mockNetworkResponseWithFileContent("success_popular_movie_response.json", HttpURLConnection.HTTP_OK)

        //Wait for MockWebServer to get back with response
        SystemClock.sleep(1000)

        Espresso.onView(withId(R.id.movie_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<MovieHomeViewHolderNormal>(0, ViewActions.click()))

        intended(hasComponent(MovieDetailActivity::class.java.name))
    }


}
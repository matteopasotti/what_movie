package com.matteopasotti.whatmovie.screens.home

import android.os.SystemClock
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.base.BaseUITest
import com.matteopasotti.whatmovie.di.generateTestAppComponent
import com.matteopasotti.whatmovie.helpers.recyclerItemAtPosition
import com.matteopasotti.whatmovie.repository.MovieRepositoryImpl
import com.matteopasotti.whatmovie.usecase.GetPopularMoviesUseCase
import com.matteopasotti.whatmovie.view.MainActivity
import com.matteopasotti.whatmovie.view.ui.HomeGalleryMoviesViewModel
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.test.inject
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4::class)
class MainActivityTest : BaseUITest() {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    val viewModel : HomeGalleryMoviesViewModel by inject()

    val useCase : GetPopularMoviesUseCase by inject()

    //val repository : MovieRepositoryImpl by inject()

    val  mMockWebServer : MockWebServer by inject()

    private val titleTestFirstItem = "Hellooooo"

    @Before
    fun start(){
        super.setUp()
        loadKoinModules(generateTestAppComponent(getMockWebServerUrl()).toMutableList())
    }

    @Test
    fun test_recyclerview_elements_for_expected_response() {
        mActivityTestRule.launchActivity(null)

        mockNetworkResponseWithFileContent("success_popular_movie_response.json", HttpURLConnection.HTTP_OK)

        //Wait for MockWebServer to get back with response
        SystemClock.sleep(3000)

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

    }


}
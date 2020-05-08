package com.matteopasotti.whatmovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.matteopasotti.whatmovie.model.Movie
import com.matteopasotti.whatmovie.repository.MovieRepositoryImpl
import com.matteopasotti.whatmovie.view.ui.HomeGalleryMoviesViewModel
import com.matteopasotti.whatmovie.view.ui.HomeMovieCategoryConstants
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE)
class HomeGalleryViewModelTest {

    /*
    We run our test class with RobolectricTestRunner. This is because ViewModel depends on
    classes within the Android SDK. We create the necessary variables, as well as mocks for
     dependencies. Note that, since TeamViewModel actually performs a transformation on
     repository.popularMovies, we need to make teamListLiveData a spy. If it were just a mock,
      the transformation wouldn’t be triggered even though viewModel.teams is being observed.
     */

    private lateinit var viewModel: HomeGalleryMoviesViewModel

    private lateinit var isLoadingLiveData: LiveData<Boolean>

    private val popularMovies = listOf(
        Movie(0 ,
            "",
            false,
            "",
            "",
            "Indiana Jones",
            "EN",
            "Indiana Jones",
            mutableListOf(),
            "",
            80.0,
            100,
            false,
            30.0),
        Movie(0 ,
            "",
            false,
            "",
            "",
            "Toy Story 4",
            "EN",
            "Toy Story 4",
            mutableListOf(),
            "",
            80.0,
            100,
            false,
            30.0)
    )

    @Spy
    private val popularMoviesLiveData: MutableLiveData<List<Movie>> = MutableLiveData()

    @Mock
    private lateinit var movieRepositoryImpl: MovieRepositoryImpl

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

//    @Before
//    fun setUp() {
//        `when`(movieRepositoryImpl.popularMovies).thenReturn(popularMoviesLiveData)
//        viewModel = HomeGalleryMoviesViewModel(movieRepositoryImpl)
//
//        isLoadingLiveData = viewModel.isLoading()
//    }
//
//    @Test
//    fun `get popular movies should show and hide loading progress bar`() {
//
//        runBlocking {
//            var isLoading = isLoadingLiveData.value
//            Assert.assertNotNull(isLoading)
//            isLoading?.let { Assert.assertTrue(it) }
//
//            viewModel.getPopularMovies(HomeMovieCategoryConstants.POPULARS)
//            verify(movieRepositoryImpl).getPopMovies()
//
//            isLoading = isLoadingLiveData.value
//            Assert.assertNotNull(isLoading)
//            isLoading?.let { Assert.assertFalse(it) }
//            return@runBlocking
//        }
//
//    }


}
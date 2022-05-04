package com.matteopasotti.whatmovie.view.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.matteopasotti.whatmovie.DataFixtures
import com.matteopasotti.whatmovie.model.toDomainModel
import com.matteopasotti.whatmovie.usecase.GetPopularMoviesUseCase
import com.matteopasotti.whatmovie.usecase.MoviesDomainModel
import com.matteopasotti.whatmovie.util.LiveDataTestUtil
import com.matteopasotti.whatmovie.util.TestMainCoroutineRule
import com.matteopasotti.whatmovie.view.ui.home.HomeGalleryMoviesViewModel
import com.matteopasotti.whatmovie.view.ui.home.HomeMovieGalleryState
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeGalleryMoviesViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = TestMainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeGalleryMoviesViewModel

    @Mock
    internal lateinit var useCase: GetPopularMoviesUseCase

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }


    private fun createViewModel() {
        viewModel = HomeGalleryMoviesViewModel(useCase)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Given we open Home Gallery screen,And we fetch all the movies,And we receive all valid responses,Then we generate the right State`() {
        val movie1 = DataFixtures.getMovie(id = 1).toDomainModel()
        val movie2 = DataFixtures.getMovie(id = 2).toDomainModel()
        val movie3 = DataFixtures.getMovie(id = 3).toDomainModel()

        runTest {
            // Given
            whenever(useCase.getMoviesAtCinema()).thenReturn(MoviesDomainModel(
                movies = listOf(movie1),
                errorMessage = null
            ))

            whenever(useCase.getPopularMovies()).thenReturn(MoviesDomainModel(
                movies = listOf(movie2),
                errorMessage = null
            ))

            whenever(useCase.getTrendingOfTheWeek()).thenReturn(MoviesDomainModel(
                movies = listOf(movie3),
                errorMessage = null
            ))

            // When
            createViewModel()

            // Then
            val state = LiveDataTestUtil.getValue(viewModel.viewState)
            assertTrue(state is HomeMovieGalleryState.Content)
            state as HomeMovieGalleryState.Content
            assertEquals(listOf(movie1), state.moviesAtCinema)
            assertEquals(listOf(movie2), state.popularMovies)
            assertEquals(listOf(movie3), state.trendingMovies)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Given we open Home Gallery screen,And we fetch all the movies,And we receive an error for at least one of them,Then we generate the right State`() {
        val movie1 = DataFixtures.getMovie(id = 1).toDomainModel()
        val movie3 = DataFixtures.getMovie(id = 3).toDomainModel()

        runTest {
            // Given
            whenever(useCase.getMoviesAtCinema()).thenReturn(MoviesDomainModel(
                movies = listOf(movie1),
                errorMessage = null
            ))

            whenever(useCase.getPopularMovies()).thenReturn(MoviesDomainModel(
                movies = emptyList(),
                errorMessage = "Error"
            ))

            whenever(useCase.getTrendingOfTheWeek()).thenReturn(MoviesDomainModel(
                movies = listOf(movie3),
                errorMessage = null
            ))

            // When
            createViewModel()

            // Then
            val state = LiveDataTestUtil.getValue(viewModel.viewState)
            assertTrue(state is HomeMovieGalleryState.Error)
        }
    }
}
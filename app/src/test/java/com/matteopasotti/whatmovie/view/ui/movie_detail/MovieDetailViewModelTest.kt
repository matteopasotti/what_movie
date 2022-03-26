package com.matteopasotti.whatmovie.view.ui.movie_detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.matteopasotti.whatmovie.DataFixtures
import com.matteopasotti.whatmovie.model.toDomainModel
import com.matteopasotti.whatmovie.usecase.MovieDetailsUseCase
import com.matteopasotti.whatmovie.usecase.MovieCreditsDomainModel
import com.matteopasotti.whatmovie.usecase.MovieDetailVModel
import com.matteopasotti.whatmovie.usecase.MoviesDomainModel
import com.matteopasotti.whatmovie.util.LiveDataTestUtil
import com.matteopasotti.whatmovie.util.TestMainCoroutineRule
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = TestMainCoroutineRule()

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MovieDetailViewModel

    @Mock
    internal lateinit var useCase: MovieDetailsUseCase

    private fun createViewModel() {
        viewModel = MovieDetailViewModel(useCase)
    }

    @Test
    fun `Given we open MovieDetailScreen,And we receive valid responses,Then we generate the right State`(){
        val movie1 = DataFixtures.getMovie(id = 1).toDomainModel()
        val movie2 = DataFixtures.getMovie(id = 2).toDomainModel()
        val actor = DataFixtures.getActor().toDomainModel()
        val movieDetail = DataFixtures.getMovieDetailResponse().toDomainModel()
        coroutineRule.testCoroutineDispatcher.runBlockingTest {
            // Given
            whenever(useCase.getRecommendedMovies(1)).thenReturn(
                MoviesDomainModel(
                movies = listOf(movie1),
                errorMessage = null)
            )

            whenever(useCase.getSimilarMovies(1)).thenReturn(
                MoviesDomainModel(
                    movies = listOf(movie2),
                    errorMessage = null)
            )

            whenever(useCase.getMovieCredits(1)).thenReturn(
                MovieCreditsDomainModel(cast = listOf(actor), errorMessage = null)
            )

            whenever(useCase.getMovieDetail(1)).thenReturn(
                MovieDetailVModel(movieDetail, errorMessage = null)
            )

            // When
            createViewModel()
            viewModel.movie = DataFixtures.getMovie(id = 1).toDomainModel()
            viewModel.getData()

            // Then
            val state = LiveDataTestUtil.getValue(viewModel.viewState)
            assertTrue(state is MovieDetailState.Content)
            state as MovieDetailState.Content
            assertEquals(listOf(movie1), state.recommendedMovies)
            assertEquals(listOf(movie2), state.similarMovies)
            assertEquals(listOf(actor), state.cast)
            assertEquals(movieDetail, state.moviesDetail)
        }
    }

    @Test
    fun `Given we open MovieDetailScreen,And we receive an error while trying to retrive MovieDetail,Then we generate the right State`(){
        val movie1 = DataFixtures.getMovie(id = 1).toDomainModel()
        val movie2 = DataFixtures.getMovie(id = 2).toDomainModel()
        val actor = DataFixtures.getActor().toDomainModel()
        coroutineRule.testCoroutineDispatcher.runBlockingTest {
            // Given
            whenever(useCase.getRecommendedMovies(1)).thenReturn(
                MoviesDomainModel(
                    movies = listOf(movie1),
                    errorMessage = null)
            )

            whenever(useCase.getSimilarMovies(1)).thenReturn(
                MoviesDomainModel(
                    movies = listOf(movie2),
                    errorMessage = null)
            )

            whenever(useCase.getMovieCredits(1)).thenReturn(
                MovieCreditsDomainModel(cast = listOf(actor), errorMessage = null)
            )

            whenever(useCase.getMovieDetail(1)).thenReturn(
                MovieDetailVModel(null, errorMessage = "Error")
            )

            // When
            createViewModel()
            viewModel.movie = DataFixtures.getMovie(id = 1).toDomainModel()
            viewModel.getData()

            // Then
            val state = LiveDataTestUtil.getValue(viewModel.viewState)
            assertTrue(state is MovieDetailState.Error)
        }
    }
}
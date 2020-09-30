package com.matteopasotti.whatmovie.usecase

import com.matteopasotti.whatmovie.DomainFixtures
import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.repository.MovieDetailRepositoryImpl
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.given
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class GetMovieDetailsUseCaseTest {

    @Mock
    internal lateinit var mockMovieDetailRepository: MovieDetailRepositoryImpl

    private lateinit var movieDetailsUseCase: GetMovieDetailsUseCase

    @Before
    fun setUp() {
        movieDetailsUseCase = GetMovieDetailsUseCase(mockMovieDetailRepository)
    }

    @Test
    fun `return a list of recommended movies`() {
        runBlocking {
            val movies = listOf(DomainFixtures.getMovie(), DomainFixtures.getMovie())

            given(mockMovieDetailRepository.getRecommendedMovies(123)).willReturn(movies)

            val result = movieDetailsUseCase.getRecommendedMovie(123)

            assertEquals(result, Result.Success(movies))
        }
    }

    @Test
    fun `return a list of actors`() {
        runBlocking {
            val actors = listOf(DomainFixtures.getActorDomainModel(), DomainFixtures.getActorDomainModel())

            given(mockMovieDetailRepository.getMovieCredits(123)).willReturn(actors)

            val result = movieDetailsUseCase.getMovieCredits(123)

            assertEquals(result, Result.Success(actors))
        }
    }

    @Test
    fun `return an error when get credits fails`() {
        runBlocking {
            given(mockMovieDetailRepository.getMovieCredits(123)).willReturn(null)

            val result = movieDetailsUseCase.getMovieCredits(123)

            assertEquals(result, Result.Error("No Data"))
        }
    }

    @Test
    fun `return an error when get recommended movies fails`() {
        runBlocking {
            given(mockMovieDetailRepository.getRecommendedMovies(123)).willReturn(null)

            val result = movieDetailsUseCase.getRecommendedMovie(123)

            assertEquals(result, Result.Error("No Data"))
        }
    }

    @Test
    fun `return an error when get recommended movies throws an exception`(){
        runBlocking {
            doThrow(IOException::class).`when`(mockMovieDetailRepository).getRecommendedMovies(1)

            val result = movieDetailsUseCase.getRecommendedMovie(1)

            assertEquals(result, Result.Error("getRecommendedMovie has failed"))
        }
    }

    @Test
    fun `return an error when get similar movies throws an exception`(){
        runBlocking {
            doThrow(IOException::class).`when`(mockMovieDetailRepository).getSimilarMovies(1)

            val result = movieDetailsUseCase.getSimilarMovies(1)

            assertEquals(result, Result.Error("getSimilarMovies has failed"))
        }
    }

    @Test
    fun `return an error when get credit movies throws an exception`(){
        runBlocking {
            doThrow(IOException::class).`when`(mockMovieDetailRepository).getMovieCredits(1)

            val result = movieDetailsUseCase.getMovieCredits(1)

            assertEquals(result, Result.Error("getMovieCredits has failed"))
        }
    }

    @Test
    fun `return an error when get movie details throws an exception`(){
        runBlocking {
            doThrow(IOException::class).`when`(mockMovieDetailRepository).getMovieDetail(1)

            val result = movieDetailsUseCase.getMovieDetail(1)

            assertEquals(result, Result.Error("getMovieDetail has failed"))
        }
    }
}
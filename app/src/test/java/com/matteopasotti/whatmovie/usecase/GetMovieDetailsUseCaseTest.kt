package com.matteopasotti.whatmovie.usecase

import com.matteopasotti.whatmovie.DomainFixtures
import com.matteopasotti.whatmovie.repository.MovieDetailRepositoryImpl
import com.nhaarman.mockitokotlin2.given
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

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

            assertEquals(result, GetMovieDetailsUseCase.Result.Success(movies))
        }
    }
}
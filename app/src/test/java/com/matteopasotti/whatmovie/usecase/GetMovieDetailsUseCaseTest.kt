package com.matteopasotti.whatmovie.usecase

import com.matteopasotti.whatmovie.DataFixtures
import com.matteopasotti.whatmovie.DomainFixtures
import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.model.response.BasicMovieResponse
import com.matteopasotti.whatmovie.model.toDomainModel
import com.matteopasotti.whatmovie.repository.MovieDetailRepository
import com.matteopasotti.whatmovie.repository.MovieDetailRepositoryImpl
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.whenever
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
    internal lateinit var repository: MovieDetailRepositoryImpl

    private lateinit var usecase: GetMovieDetailsUseCase

    private val movies = listOf(DataFixtures.getMovie(id = 1))

    private val successResponse =
        BasicMovieResponse(page = 1, results = movies)

    @Before
    fun setUp() {
        usecase = GetMovieDetailsUseCase(repository)
    }

    @Test
    fun `Given we call getRecommendedMovies,And we receive a valid response from repository,Then we return the right object`() {
        runBlocking {
            whenever(repository.getRecommendedMovies(1)).thenReturn(
                Result.Success(successResponse)
            )

            val actual = usecase.getRecommendedMovies(1)

            val expected = MoviesDomainModel(
                movies = movies.map { it.toDomainModel() },
                errorMessage = null
            )

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `Given we call getRecommendedMovies,And we receive an error,Then we return the right object`() {
        runBlocking {
            whenever(repository.getRecommendedMovies(1)).thenReturn(
                Result.Error("Error unable to retrieve movies")
            )

            val actual = usecase.getRecommendedMovies(1)

            val expected = MoviesDomainModel(
                movies = emptyList(),
                errorMessage = "Error unable to retrieve movies"
            )

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `Given we call getSimilarMovies,And we receive a valid response from repository,Then we return the right object`() {
        runBlocking {
            whenever(repository.getSimilarMovies(1)).thenReturn(
                Result.Success(successResponse)
            )

            val actual = usecase.getSimilarMovies(1)

            val expected = MoviesDomainModel(
                movies = movies.map { it.toDomainModel() },
                errorMessage = null
            )

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `Given we call getSimilarMovies,And we receive a valid response from repository,And some movies do not have an image,Then we return the right object`() {

        val movie1 = DataFixtures.getMovie(id = 1)
        val movie2 = DataFixtures.getMovie(id = 2, poster_path = null)
        val movies = listOf(movie1, movie2)

        val response =
            BasicMovieResponse(page = 1, results = movies)

        runBlocking {
            whenever(repository.getSimilarMovies(1)).thenReturn(
                Result.Success(response)
            )

            val actual = usecase.getSimilarMovies(1)

            val expected = MoviesDomainModel(
                movies = listOf(movie1.toDomainModel()),
                errorMessage = null
            )

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `Given we call getSimilarMovies,And we receive an error,Then we return the right object`() {
        runBlocking {
            whenever(repository.getSimilarMovies(1)).thenReturn(
                Result.Error("Error unable to retrieve movies")
            )

            val actual = usecase.getSimilarMovies(1)

            val expected = MoviesDomainModel(
                movies = emptyList(),
                errorMessage = "Error unable to retrieve movies"
            )

            assertEquals(expected, actual)
        }
    }

}
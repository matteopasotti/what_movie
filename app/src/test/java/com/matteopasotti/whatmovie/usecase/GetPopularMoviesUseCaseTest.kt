package com.matteopasotti.whatmovie.usecase

import com.matteopasotti.whatmovie.DataFixtures
import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.model.response.BasicMovieResponse
import com.matteopasotti.whatmovie.model.toDomainModel
import com.matteopasotti.whatmovie.repository.MovieRepository
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class GetPopularMoviesUseCaseTest {


    @Mock
    internal lateinit var movieRepository: MovieRepository

    private lateinit var useCase: GetPopularMoviesUseCase

    private val movies = listOf(DataFixtures.getMovie(id = 1))

    private val successResponse =
        BasicMovieResponse(page = 1, results = movies)

    @Before
    fun setUp() {
        useCase = GetPopularMoviesUseCase(movieRepository)
    }

    @Test
    fun `Given we call getPopularMovies,And we receive some movies,Then we return the right object`() {
        runBlocking {
            whenever(movieRepository.getPopularMoviesFromApi()).thenReturn(
                Result.Success(successResponse)
            )

            val actual = useCase.getPopularMovies()

            val expected = MoviesDomainModel(
                movies = movies.map { it.toDomainModel() },
                errorMessage = null
            )

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `Given we call getPopularMovies,And we receive an error,Then we return the right object`() {
        runBlocking {
            whenever(movieRepository.getPopularMoviesFromApi()).thenReturn(
                Result.Error("Error unable to retrieve movies")
            )

            val actual = useCase.getPopularMovies()

            val expected = MoviesDomainModel(
                movies = emptyList(),
                errorMessage = "Error unable to retrieve movies"
            )

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `Given we call getMoviesAtCinema,And we received some movies,Then we return the right object`() {
        runBlocking {
            whenever(movieRepository.getMoviesAtTheatre()).thenReturn(
                Result.Success(successResponse)
            )

            val actual = useCase.getMoviesAtCinema()

            val expected = MoviesDomainModel(
                movies = movies.map { it.toDomainModel() },
                errorMessage = null
            )

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `Given we call getMoviesAtCinema,And we receive an error,Then we return the right object`() {
        runBlocking {
            whenever(movieRepository.getMoviesAtTheatre()).thenReturn(
                Result.Error("Error unable to retrieve movies")
            )

            val actual = useCase.getMoviesAtCinema()

            val expected = MoviesDomainModel(
                movies = emptyList(),
                errorMessage = "Error unable to retrieve movies"
            )

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `Given we call getTrendingOfTheWeek,And we receive some movies,Then we return the right object`() {
        runBlocking {
            whenever(movieRepository.getTrendingOfTheWeek()).thenReturn(
                Result.Success(successResponse)
            )

            val actual = useCase.getTrendingOfTheWeek()

            val expected = MoviesDomainModel(
                movies = movies.map { it.toDomainModel() },
                errorMessage = null
            )

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `Given we call getTrendingOfTheWeek,And we receive an error,Then we return the right object`() {
        runBlocking {
            whenever(movieRepository.getTrendingOfTheWeek()).thenReturn(
                Result.Error("Error unable to retrieve movies")
            )

            val actual = useCase.getTrendingOfTheWeek()

            val expected = MoviesDomainModel(
                movies = emptyList(),
                errorMessage = "Error unable to retrieve movies"
            )

            assertEquals(expected, actual)
        }
    }


}
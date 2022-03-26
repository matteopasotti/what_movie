package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.DataFixtures
import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.db.MovieDao
import com.matteopasotti.whatmovie.model.response.BasicMovieResponse
import com.matteopasotti.whatmovie.util.TestMainCoroutineRule
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryImplTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = TestMainCoroutineRule()

    @Mock
    internal lateinit var movieApiInterface: MovieApiInterface

    @Mock
    internal lateinit var movieDao: MovieDao

    private lateinit var repository: MovieRepository

    private val successResponse =
        BasicMovieResponse(page = 1, results = listOf(DataFixtures.getMovie()))

    private val errorContent =
        "{\"status_code\":7,\"status_message\":\"Invalid API key: You must be granted a valid key.\",\"success\":false}"

    @Before
    fun setUp() {
        repository = MovieRepositoryImpl(
            movieApi = movieApiInterface,
            movieDao = movieDao,
            ioDispatcher = coroutineRule.testCoroutineDispatcher
        )
    }

    @Test
    fun `Given we call getMoviesInCinema,And We get a valid response from Api,Then we return Result Success`() {

        coroutineRule.runBlockingTest {
            whenever(
                movieApiInterface.getMoviesInCinema(
                    page = 1,
                    startDate = getStartDate(),
                    endDate = getEndDate()
                )
            ).thenReturn(
                Response.success(
                    200,
                    successResponse
                )
            )

            val expected = Result.Success(successResponse)
            val actual = repository.getMoviesAtTheatre()

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `Given we call getMoviesInCinema,And we get an Error from Api,Then we return Result Error`() {

        coroutineRule.runBlockingTest {
            whenever(
                movieApiInterface.getMoviesInCinema(
                    page = 1,
                    startDate = getStartDate(),
                    endDate = getEndDate()
                )
            ).thenReturn(Response.error(400, errorContent.toResponseBody()))

            val actual = repository.getMoviesAtTheatre()

            assertTrue(actual is Result.Error)
        }
    }

    @Test
    fun `Given we call getTrendingOfTheWeek,And we get a valid response from Api,Then we return Success`() {
        coroutineRule.runBlockingTest {
            whenever(movieApiInterface.getTrendingOfTheWeek()).thenReturn(
                Response.success(
                    200,
                    successResponse
                )
            )

            val expected = Result.Success(successResponse)
            val actual = repository.getTrendingOfTheWeek()

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `Given we call getTrendingOfTheWeek,And we get an error from Api,Then we return Error`() {
        coroutineRule.runBlockingTest {
            whenever(
                movieApiInterface.getTrendingOfTheWeek()
            ).thenReturn(Response.error(400, errorContent.toResponseBody()))

            val actual = repository.getTrendingOfTheWeek()

            assertTrue(actual is Result.Error)
        }
    }

    @Test
    fun `Given we call getPopularMoviesFromApi,And we get a valid response from Api,Then we return Success`() {
        coroutineRule.runBlockingTest {
            whenever(movieApiInterface.getPopularMovies()).thenReturn(
                Response.success(
                    200,
                    successResponse
                )
            )

            val expected = Result.Success(successResponse)
            val actual = repository.getPopularMoviesFromApi()

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `Given we call getPopularMoviesFromApi,And we get an error from Api,Then we return Error`() {
        coroutineRule.runBlockingTest {
            whenever(movieApiInterface.getPopularMovies()).thenReturn(
                Response.error(400, errorContent.toResponseBody())
            )

            val actual = repository.getPopularMoviesFromApi()

            assertTrue(actual is Result.Error)
        }
    }

    private fun getStartDate(): String {
        val currentTime: Date = Calendar.getInstance().time
        return formatDate(currentTime)
    }

    private fun getEndDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, 1)
        val date = calendar.time
        return formatDate(date)
    }

    private fun formatDate(date: Date): String {
        val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return df.format(date)
    }
}
package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.DataFixtures
import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.model.response.BasicMovieResponse
import com.matteopasotti.whatmovie.model.response.MovieCreditResponse
import com.matteopasotti.whatmovie.util.TestMainCoroutineRule
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class MovieDetailRepositoryImplTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = TestMainCoroutineRule()

    @Mock
    internal lateinit var movieApi: MovieApiInterface

    private lateinit var repository: MovieDetailRepositoryImpl

    private val successResponse =
        BasicMovieResponse(page = 1, results = listOf(DataFixtures.getMovie()))

    private val errorContent =
        "{\"status_code\":7,\"status_message\":\"Invalid API key: You must be granted a valid key.\",\"success\":false}"

    @Before
    fun setUp() {
        repository = MovieDetailRepositoryImpl(movieApi, coroutineRule.testCoroutineDispatcher)
    }

    @Test
    fun `Given we call getRecommendedMovies,And We get a valid response from Api,Then we return Result Success`() {
        coroutineRule.runBlockingTest {
            whenever(
                movieApi.getRecommendedMovies(
                    1, 1
                )
            ).thenReturn(
                Response.success(
                    200,
                    successResponse
                )
            )

            val expected = Result.Success(successResponse)
            val actual = repository.getRecommendedMovies(1)

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `Given we call getRecommendedMovies,And we get an Error from Api,Then we return Result Error`() {
        coroutineRule.runBlockingTest {
            whenever(
                movieApi.getRecommendedMovies(
                    1, 1
                )
            ).thenReturn(Response.error(400, errorContent.toResponseBody()))

            val actual = repository.getRecommendedMovies(1)

            Assert.assertTrue(actual is Result.Error)
        }
    }

    @Test
    fun `Given we call getSimilarMovies,And We get a valid response from Api,Then we return Result Success`() {
        coroutineRule.runBlockingTest {
            whenever(
                movieApi.getSimilarMovies(
                    1, 1
                )
            ).thenReturn(
                Response.success(
                    200,
                    successResponse
                )
            )

            val expected = Result.Success(successResponse)
            val actual = repository.getSimilarMovies(1)

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `Given we call getSimilarMovies,And we get an Error from Api,Then we return Result Error`() {
        coroutineRule.runBlockingTest {
            whenever(
                movieApi.getSimilarMovies(
                    1, 1
                )
            ).thenReturn(Response.error(400, errorContent.toResponseBody()))

            val actual = repository.getSimilarMovies(1)

            Assert.assertTrue(actual is Result.Error)
        }
    }

    @Test
    fun `Given we call getMovieCredits,And We get a valid response from Api,Then we return Result Success`() {
        val creditResponseSuccess = MovieCreditResponse(11, listOf(DataFixtures.getActor()))
        coroutineRule.runBlockingTest {
            whenever(
                movieApi.getMovieCredits(
                    1
                )
            ).thenReturn(
                Response.success(
                    200,
                    creditResponseSuccess
                )
            )

            val expected = Result.Success(creditResponseSuccess)
            val actual = repository.getMovieCredits(1)

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `Given we call getMovieCredits,And We get an Error from Api,Then we return Result Error`() {
        coroutineRule.runBlockingTest {
            whenever(
                movieApi.getMovieCredits(
                    1
                )
            ).thenReturn(
                Response.error(400, errorContent.toResponseBody())
            )

            val actual = repository.getMovieCredits(1)

            Assert.assertTrue(actual is Result.Error)
        }
    }

    @Test
    fun `Given we call getMovieDetail,And We get a valid response from Api,Then we return Result Success`() {
        val movieDetailResponseSuccess = DataFixtures.getMovieDetailResponse()
        coroutineRule.runBlockingTest {
            whenever(
                movieApi.getMovieDetail(
                    1
                )
            ).thenReturn(
                Response.success(
                    200,
                    movieDetailResponseSuccess
                )
            )

            val expected = Result.Success(movieDetailResponseSuccess)
            val actual = repository.getMovieDetail(1)

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `Given we call getMovieDetail,And We get an error from Api,Then we return Result Error`() {
        coroutineRule.runBlockingTest {
            whenever(
                movieApi.getMovieDetail(
                    1
                )
            ).thenReturn(
                Response.error(400, errorContent.toResponseBody())
            )

            val actual = repository.getMovieDetail(1)

            Assert.assertTrue(actual is Result.Error)
        }
    }
}
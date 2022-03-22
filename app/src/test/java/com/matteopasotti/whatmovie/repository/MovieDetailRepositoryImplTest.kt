package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.BuildConfig
import com.matteopasotti.whatmovie.DataFixtures
import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.matteopasotti.whatmovie.model.response.BasicMovieResponse
import com.matteopasotti.whatmovie.model.response.MovieCreditResponse
import com.matteopasotti.whatmovie.model.toDomainModel
import com.nhaarman.mockitokotlin2.given
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class MovieDetailRepositoryImplTest {

    @Mock
    internal lateinit var mockService: MovieApiInterface

    private lateinit var repository: MovieDetailRepositoryImpl

    @Before
    fun setUp() {
        repository = MovieDetailRepositoryImpl(mockService)
    }

    @Test
    fun `getRecommendedMovies fetches Movies and map them to domainModel`() {
        runBlocking {
            given(mockService.getRecommendedMovies(1, 1))
                .willReturn(
                    Response.success(
                        200, BasicMovieResponse(
                            page = 1,
                            results = listOf(DataFixtures.getMovie())
                        )
                    )
                )

            val result = repository.getRecommendedMovies(1)

            assertEquals(result!!.first(), DataFixtures.getMovie().toDomainModel())
        }
    }

    @Test
    fun `getRecommendedMovies returns error then we do not return any recommended movie`() {
        val content =
            "{\"status_code\":7,\"status_message\":\"Invalid API key: You must be granted a valid key.\",\"success\":false}"
        runBlocking {
            given(mockService.getRecommendedMovies(1, 1))
                .willReturn(
                    Response.error(400, ResponseBody.create(null, content))
                )

            val result = repository.getRecommendedMovies(1)

            assertEquals(result, null)
        }
    }

    @Test
    fun `getSimilarMovies fetches Movies and map them to domainModel`() {
        runBlocking {
            given(mockService.getSimilarMovies(1, 1))
                .willReturn(
                    Response.success(
                        200, BasicMovieResponse(
                            page = 1,
                            results = listOf(DataFixtures.getMovie())
                        )
                    )
                )

            val result = repository.getSimilarMovies(1)

            assertEquals(result!!.first(), DataFixtures.getMovie().toDomainModel())
        }
    }

    @Test
    fun `getSimilarMovies returns error then we do not return any similar movie`() {
        val content =
            "{\"status_code\":7,\"status_message\":\"Invalid API key: You must be granted a valid key.\",\"success\":false}"
        runBlocking {
            given(mockService.getSimilarMovies(1, 1))
                .willReturn(
                    Response.error(400, ResponseBody.create(null, content))
                )

            val result = repository.getSimilarMovies(1)

            assertEquals(result, null)
        }
    }

    @Test
    fun `getMovieCredits fetches credits and map them to domainModel`() {
        runBlocking {
            given(mockService.getMovieCredits(1))
                .willReturn(
                    Response.success(200, MovieCreditResponse(
                        id = 1,
                        cast = listOf(DataFixtures.getActor())
                    ))
                )

            val result = repository.getMovieCredits(1)

            assertEquals(DataFixtures.getActor().toDomainModel(), result!!.first())
        }
    }

    @Test
    fun `getMovieCredits fetches credits and filters out the ones with no profile image`(){
        val actorWithImage = DataFixtures.getActor(profile_path = "image")
        val actorWithoutImage = DataFixtures.getActor(profile_path = null)
        runBlocking {
            given(mockService.getMovieCredits(1))
                .willReturn(
                    Response.success(200, MovieCreditResponse(
                        id = 1,
                        cast = listOf(actorWithImage, actorWithoutImage)
                    ))
                )

            val result = repository.getMovieCredits(1)

            assertEquals(1, result!!.size)
            assertEquals(actorWithImage.toDomainModel(), result!!.first())
        }
    }

    @Test
    fun `getMovieCredits returns error and does not return any actor`(){
        val content =
            "{\"status_code\":7,\"status_message\":\"Invalid API key: You must be granted a valid key.\",\"success\":false}"
        runBlocking {
            given(mockService.getMovieCredits(1))
                .willReturn(
                    Response.error(400, ResponseBody.create(null, content))
                )

            val result = repository.getMovieCredits(1)

            assertEquals(null, result)
        }
    }

    @Test
    fun `getMovieDetail returns movie detail and map it to domainModel`(){
        runBlocking {
            given(mockService.getMovieDetail(1))
                .willReturn(
                    Response.success(200, DataFixtures.getMovieDetailResponse())
                )

            val result = repository.getMovieDetail(1)

            assertEquals(DataFixtures.getMovieDetailResponse().toDomainModel(), result)
        }
    }
}
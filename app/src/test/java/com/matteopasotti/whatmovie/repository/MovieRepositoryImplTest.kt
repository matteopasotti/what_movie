package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.BuildConfig
import com.matteopasotti.whatmovie.DataFixtures
import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.matteopasotti.whatmovie.db.MovieDao
import com.matteopasotti.whatmovie.model.response.PopularMovieResponse
import com.matteopasotti.whatmovie.model.toDomainModel
import com.nhaarman.mockitokotlin2.given
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryImplTest {

    @Mock
    internal lateinit var mockService: MovieApiInterface

    @Mock
    internal lateinit var movieDao: MovieDao

    private lateinit var repository: MovieRepositoryImpl

    private val language = "en-US"

    private val page = 1

    private val apiKey = BuildConfig.API_KEY

    @Before
    fun setUp() {
        repository = MovieRepositoryImpl(mockService, movieDao)
    }

    @Test
    fun `getPopularMovies fetches Movie and maps to Model`() {
        runBlocking {
            given(mockService.getPopularMovies(apiKey, language, page))
                .willReturn(
                    Response.success(200, PopularMovieResponse(
                        page = 1,
                        results = listOf(DataFixtures.getMovie())
                    ))
                )

            val result = repository.getPopularMovies(1)

            assertEquals(result!!.first(), DataFixtures.getMovie().toDomainModel())
        }
    }

    @Test
    fun `getPopularMovies fetches Movie and filters out the ones without a poster image`() {
        val movieWithImage = DataFixtures.getMovie(poster_path = "image")
        val movieWithoutImage = DataFixtures.getMovie(poster_path = null)
        runBlocking {
            given(mockService.getPopularMovies(apiKey, language, page))
                .willReturn(
                    Response.success(200, PopularMovieResponse(
                        page = 1,
                        results = listOf(movieWithImage, movieWithoutImage)
                    ))
                )

            val result = repository.getPopularMovies(1)

            assertTrue(result!!.size == 1)
            assertEquals(result.first(), movieWithImage.toDomainModel())
        }
    }

    @Test
    fun `getPopularMovies returns error and we do not return any movie`() {
        val content = "{\"status_code\":7,\"status_message\":\"Invalid API key: You must be granted a valid key.\",\"success\":false}"
        runBlocking {
            given(mockService.getPopularMovies(apiKey, language, page))
                .willReturn(
                    Response.error(400, content.toResponseBody())
                )

            val result = repository.getPopularMovies(1)

            assertEquals(result, null)
        }
    }


    @Test
    fun `Given we are fetching page 2, AND lastFetchedPageFromDb return 1, then we fetch data from API`() {
        val movieFromAPI = DataFixtures.getMovie(id = 4)
        val pageDB = 1
        val pageAPI = 2

        runBlocking {
            given(movieDao.getLastPageFetched()).willReturn(pageDB)

            given(mockService.getPopularMovies(apiKey, language, pageAPI))
                .willReturn(
                    Response.success(
                        PopularMovieResponse(
                            page = 1,
                            results = listOf(movieFromAPI)
                        )
                    )

                )

            val result = repository.getPopularMovies(2)

            assertEquals(result!!.first().id, movieFromAPI.id)
        }
    }

    @Test
    fun `Given we are fetching page 1, AND lastFetchedPageFromDb returns 1, then we fetch data from db`(){
        val movie = DataFixtures.getMovie(id = 4)
        runBlocking {
            given(movieDao.getLastPageFetched()).willReturn(1)

            given(movieDao.getMoviesByPage(1)).willReturn(listOf(movie))

            val result = repository.getPopularMovies(1)

            assertEquals(result!!.first().id, movie.id)
        }
    }

    @Test
    fun `Given lastFetchedPageFromDb returns null, then fetch data from api`() {
        val movie = DataFixtures.getMovie(id = 4)
        runBlocking {
            given(movieDao.getLastPageFetched()).willReturn(null)
            given(mockService.getPopularMovies(apiKey, language, page))
                .willReturn(
                    Response.success(
                        PopularMovieResponse(
                            page = 1,
                            results = listOf(movie)
                        )
                    )

                )

            val result = repository.getPopularMovies(1)

            assertEquals(result!!.first().id, movie.id)
        }
    }
}
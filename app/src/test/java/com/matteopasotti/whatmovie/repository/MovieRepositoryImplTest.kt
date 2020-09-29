package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.BuildConfig
import com.matteopasotti.whatmovie.DataFixtures
import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.matteopasotti.whatmovie.db.MovieDao
import com.matteopasotti.whatmovie.model.response.PopularMovieResponse
import com.matteopasotti.whatmovie.model.toDomainModel
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryImplTest {

    @Mock
    internal lateinit var mockService: MovieApiInterface

    @Mock
    internal lateinit var movieDao: MovieDao

    @Mock
    internal lateinit var syncRepo: SyncRepoImpl

    private lateinit var repository: MovieRepositoryImpl

    private val language = "en-US"

    private val page = 1

    private val apiKey = BuildConfig.API_KEY

    @Before
    fun setUp() {
        repository = MovieRepositoryImpl(mockService, movieDao, syncRepo)
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
    fun `Given data out of sync, Then we get movies from API`() {
        doReturn(false).`when`(syncRepo).areDataUpdated()

        runBlocking {
            given(mockService.getPopularMovies(apiKey, language, page))
                .willReturn(Response.success(PopularMovieResponse(
                    page = 1,
                    results = listOf(DataFixtures.getMovie())
                )))

            val result = repository.getPopularMovies(1)

            assertEquals(result!!.first(), DataFixtures.getMovie().toDomainModel())
        }

    }

    @Test
    fun `Given data are syncronized, And is the first access, Then we get all the movies from db`(){
        doReturn(true).`when`(syncRepo).areDataUpdated()

        repository.firstAccess = true

        runBlocking {
           given(movieDao.getMovies()).willReturn(listOf(DataFixtures.getMovie()))

            val result = repository.getPopularMovies(1)

            assertEquals(result!!.first(), DataFixtures.getMovie().toDomainModel())
        }
    }

    @Test
    fun `Given data are syncronized but firstAccess is false, And we have other movies in the db, Then we get the movies from db for that page`(){
        doReturn(true).`when`(syncRepo).areDataUpdated()
        repository.firstAccess = false

        runBlocking {
            given(movieDao.getMoviesByPage(1)).willReturn(listOf(DataFixtures.getMovie(id = 123)))

            val result = repository.getPopularMovies(1)

            assertEquals(result!!.first(), DataFixtures.getMovie().toDomainModel())
        }
    }

    @Test
    fun `Given data are syncronized but firstAccess is false, And we do not have other movies in the db, Then we get the movies from API`(){
        doReturn(true).`when`(syncRepo).areDataUpdated()
        repository.firstAccess = false

        val movie = DataFixtures.getMovie(id = 4)

        runBlocking {
            given(movieDao.getMoviesByPage(1)).willReturn(listOf())

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
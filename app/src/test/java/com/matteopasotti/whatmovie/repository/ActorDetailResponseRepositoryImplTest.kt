package com.matteopasotti.whatmovie.repository

import com.matteopasotti.whatmovie.BuildConfig
import com.matteopasotti.whatmovie.DataFixtures
import com.matteopasotti.whatmovie.api.MovieApiInterface
import com.nhaarman.mockitokotlin2.given
import junit.framework.Assert
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response


@RunWith(MockitoJUnitRunner::class)
class ActorDetailResponseRepositoryImplTest {


    @Mock
    internal lateinit var apiService: MovieApiInterface

    private lateinit var repository: ActorDetailRepositoryImpl

    @Before
    fun setUp() {
        repository = ActorDetailRepositoryImpl(apiService)
    }

    @Test
    fun `getActorDetails fetch actor details and map it to domainModel`(){
        runBlocking {
            given(apiService.getActorDetails(1, BuildConfig.API_KEY, "en-US"))
                .willReturn(
                    Response.success(
                        200, DataFixtures.getActorDetail()
                    )
                )

            val result = repository.getActorDetails(1)

            assertEquals(result!!.name, DataFixtures.getActorDetail().name)
        }
    }

    @Test
    fun `getActorDetails returns an error and we return null`(){
        val content =
            "{\"status_code\":7,\"status_message\":\"Invalid API key: You must be granted a valid key.\",\"success\":false}"
        runBlocking {
            given(apiService.getActorDetails(1, BuildConfig.API_KEY, "en-US"))
                .willReturn(Response.error(400, content.toResponseBody()))

            val result = repository.getActorDetails(1)

            Assert.assertEquals(result, null)


        }
    }
}
package com.matteopasotti.whatmovie.usecase

import com.matteopasotti.whatmovie.DomainFixtures
import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.repository.MovieRepositoryImpl
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
class GetPopularMoviesUseCaseTest {


    @Mock
    internal lateinit var mockMovieRepository: MovieRepositoryImpl

    private lateinit var useCase: GetPopularMoviesUseCase

    @Before
    fun setUp() {
        useCase = GetPopularMoviesUseCase(mockMovieRepository)
    }

    @Test
    fun `return list of movies`() {
        runBlocking {
            val movies = listOf(DomainFixtures.getMovie(), DomainFixtures.getMovie())

            given(mockMovieRepository.getPopularMovies(1)).willReturn(movies)

            val result = useCase.execute()

            assertEquals(result, Result.Success(movies))
        }
    }

    @Test
    fun `return an error`(){
        runBlocking {
            given(mockMovieRepository.getPopularMovies(1)).willReturn(null)

            val result = useCase.execute()

            assertEquals(result, Result.Error("No Data"))
        }
    }

    @Test
    fun `Given getPopularMovies throws an Exception, Then we return Error`(){
        runBlocking {
            doThrow(IOException::class).`when`(mockMovieRepository).getPopularMovies(1)

            val result = useCase.execute()

            assertEquals(result, Result.Error("getPopularMovies error"))
        }
    }
}
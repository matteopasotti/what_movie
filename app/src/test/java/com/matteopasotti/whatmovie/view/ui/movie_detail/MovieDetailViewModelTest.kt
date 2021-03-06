package com.matteopasotti.whatmovie.view.ui.movie_detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.matteopasotti.whatmovie.DomainFixtures
import com.matteopasotti.whatmovie.api.Result
import com.matteopasotti.whatmovie.usecase.GetMovieDetailsUseCase
import com.matteopasotti.whatmovie.util.CoroutineRule
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineRule()

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MovieDetailViewModel

    private lateinit var isLoadingLiveData: LiveData<Boolean>

    private lateinit var isErrorLiveData: LiveData<Boolean>

    @Mock
    internal lateinit var useCase: GetMovieDetailsUseCase

    @Before
    fun setUp() {
        viewModel = MovieDetailViewModel(useCase)

        viewModel.movie = DomainFixtures.getMovie()

        isLoadingLiveData = viewModel.isLoading()

        isErrorLiveData = viewModel.isError()
    }

    @Test
    fun `execute movie detail usecase`() {
        viewModel.getData()

        runBlocking {
            verify(useCase).getRecommendedMovie(123)
        }

    }

    @Test
    fun `getData show and hide progress bar when get recommended movies succeed`() {

        runBlocking {

            given(useCase.getRecommendedMovie(123)).willReturn(Result.Success(listOf(DomainFixtures.getMovie())))

            var isLoading = isLoadingLiveData.value
            assertNotNull(isLoading)
            isLoading?.let { Assert.assertTrue(it) }

            viewModel.getData()
            verify(useCase).getRecommendedMovie(123)

            isLoading = isLoadingLiveData.value
            assertNotNull(isLoading)
            isLoading?.let { assertFalse(it) }

            return@runBlocking
        }
    }

    @Test
    fun `getData show and hide progress bar when get credits succeed`() {

        runBlocking {

            given(useCase.getMovieCredits(123)).willReturn(Result.Success(listOf(DomainFixtures.getActorDomainModel())))

            var isLoading = isLoadingLiveData.value
            assertNotNull(isLoading)
            isLoading?.let { Assert.assertTrue(it) }

            viewModel.getData()
            verify(useCase).getMovieCredits(123)

            isLoading = isLoadingLiveData.value
            assertNotNull(isLoading)
            isLoading?.let { assertFalse(it) }

            return@runBlocking
        }
    }

    @Test
    fun `getData show error when get credits fails`() {
        runBlocking {

            given(useCase.getMovieCredits(123)).willReturn(Result.Error("Error"))

            var isLoading = isLoadingLiveData.value
            assertNotNull(isLoading)
            isLoading?.let { Assert.assertTrue(it) }

            var isError = isErrorLiveData.value
            assertNotNull(isError)
            isError?.let { assertFalse(it) }

            viewModel.getData()
            verify(useCase).getRecommendedMovie(123)

            isLoading = isLoadingLiveData.value
            assertNotNull(isLoading)
            isLoading?.let { assertFalse(it) }

            isError = isErrorLiveData.value
            assertNotNull(isError)
            isError?.let { assertTrue(it) }

            return@runBlocking
        }
    }

    @Test
    fun `getData show error when get recommended movies fails`() {
        runBlocking {

            given(useCase.getRecommendedMovie(123)).willReturn(Result.Error("Error"))

            var isLoading = isLoadingLiveData.value
            assertNotNull(isLoading)
            isLoading?.let { Assert.assertTrue(it) }

            var isError = isErrorLiveData.value
            assertNotNull(isError)
            isError?.let { assertFalse(it) }

            viewModel.getData()
            verify(useCase).getRecommendedMovie(123)

            isLoading = isLoadingLiveData.value
            assertNotNull(isLoading)
            isLoading?.let { assertFalse(it) }

            isError = isErrorLiveData.value
            assertNotNull(isError)
            isError?.let { assertTrue(it) }

            return@runBlocking
        }
    }
}
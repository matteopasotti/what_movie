package com.matteopasotti.whatmovie.view.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.matteopasotti.whatmovie.DomainFixtures
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.usecase.GetPopularMoviesUseCase
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
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeGalleryMoviesViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineRule()

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeGalleryMoviesViewModel

    private lateinit var isLoadingLiveData: LiveData<Boolean>

    @Mock
    internal lateinit var mockGetPopularMoviesUseCase: GetPopularMoviesUseCase

    @Before
    fun setUp() {

        viewModel = HomeGalleryMoviesViewModel(mockGetPopularMoviesUseCase)

        isLoadingLiveData = viewModel.isLoading()



    }

    @Test
    fun `execute getPopularMoviesUseCase`() {
        // when
        viewModel.getPopularMovies()

        // then
        runBlocking {
            verify(mockGetPopularMoviesUseCase).execute()
        }
    }

    @Test
    fun `GetPopularMovies shows and hides loading progress bar`() {

        viewModel.getMovies()

        runBlocking {

            given(mockGetPopularMoviesUseCase.execute()).willReturn(listOf(DomainFixtures.getMovie()))

            var isLoading = isLoadingLiveData.value
            Assert.assertNotNull(isLoading)
            isLoading?.let { Assert.assertTrue(it) }

            viewModel.getPopularMovies()
            verify(mockGetPopularMoviesUseCase).execute()

            isLoading = isLoadingLiveData.value
            Assert.assertNotNull(isLoading)
            isLoading?.let { Assert.assertFalse(it) }

            return@runBlocking
        }
    }
}
package com.vk.presentation

import app.cash.turbine.test
import com.vk.domain.model.VideoInfo
import com.vk.domain.usecases.GetVideosUsecase
import com.vk.presentation.viewmodel.VideoListViewmodel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

class VideoListViewModelTest: KoinComponent {

    private val getVideosUsecase: GetVideosUsecase = mockk()

    private val testModule = module {
        viewModel { VideoListViewmodel(get()) }
        single { getVideosUsecase }
    }

    @Before
    fun setUp() {
        startKoin {
            modules(testModule)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `loadVideos should emit loading state then success`() = runTest {
        val mockVideoList = listOf(VideoInfo("3284324", "Test Video", "url", duration = 15, srcUrl = "url"))
        coEvery { getVideosUsecase() } returns mockVideoList

        val viewModel: VideoListViewmodel = get()

        viewModel.loadVideos()

        viewModel.isLoading.test {
            assertEquals(true, awaitItem())
            assertEquals(false, awaitItem())
        }

        viewModel.videos.test {
            assertEquals(mockVideoList, awaitItem())
        }

        viewModel.error.test {
            assertEquals("Network error", awaitItem())
        }
    }

    @Test
    fun `loadVideos should emit loading then error state when useCase fails`() = runTest {
        coEvery { getVideosUsecase() } throws Exception("Network error")

        val viewModel: VideoListViewmodel = get()

        viewModel.loadVideos()

        viewModel.isLoading.test {
            assertEquals(true, awaitItem())  // Начало загрузки
            assertEquals(false, awaitItem()) // Завершение загрузки
        }

        viewModel.videos.test {
            assertEquals(emptyList<VideoInfo>(), awaitItem())
        }

        viewModel.error.test {
            assertEquals("Network error", awaitItem())
        }
    }




}
package com.vk.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.vk.domain.model.VideoInfo
import com.vk.presentation.ui.VideoPlayerScreen
import com.vk.presentation.viewmodel.VideoPlayerViewModel
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class VideoPlayerScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockVideoPlayerViewModel: VideoPlayerViewModel = mockk()

    private val video = VideoInfo(
        id = "1",
        title = "Test Video",
        thumbnailUrl = "testThumbnailUrl",
        duration = 120,
        srcUrl = "testVideoUrl"
    )

    private val testModule = module {
        single { mockVideoPlayerViewModel }
    }

    @Before
    fun setup() {
        startKoin { modules(testModule) }
    }

    @After
    fun teardown() {
        stopKoin()
    }

    @Test
    fun videoPlayerScreen_DisplaysCorrectly() {

        coEvery { mockVideoPlayerViewModel.isFullScreen } returns false

        composeTestRule.setContent {
            VideoPlayerScreen(video = video, onBack = {})
        }

        composeTestRule.onNodeWithText("Воспроизведение видео").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Назад").assertIsDisplayed()
    }

    @Test
    fun videoPlayerScreen_HandlesBackNavigation() {
        var backClicked = false
        composeTestRule.setContent {
            VideoPlayerScreen(video = video, onBack = { backClicked = true })
        }

        composeTestRule.onNodeWithContentDescription("Назад").performClick()
        assert(backClicked)
    }

    @Test
    fun videoPlayerScreen_ShowsVideoPlayer() {
        coEvery { mockVideoPlayerViewModel.isFullScreen } returns false

        composeTestRule.setContent {
            VideoPlayerScreen(video = video, onBack = {})
        }

        // Проверка, что видеоплеер отображается
        composeTestRule.onNodeWithTag("PlayerView").assertIsDisplayed()
    }


}
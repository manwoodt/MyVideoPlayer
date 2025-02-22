package com.vk.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.vk.domain.model.VideoInfo
import com.vk.domain.usecases.GetVideosUsecase
import com.vk.presentation.navigation.NavigationScreen
import com.vk.presentation.viewmodel.VideoListViewmodel
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


class NavigationScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val videoList = listOf(
        VideoInfo(
            id = "1",
            title = "Test Video",
            thumbnailUrl = "thumbnailUrl1",
            duration = 120,
            srcUrl = "srcUrl1"
        ),
        VideoInfo(
            id = "2",
            title = "Another Video",
            thumbnailUrl = "url/thumbnail2.jpg",
            duration = 90,
            srcUrl = "srcUrl2"
        )
    )

    private val getVideosUsecase: GetVideosUsecase = mockk()

    private val testModule = module {
        viewModel { VideoListViewmodel(get()) }
        single { getVideosUsecase }
    }


    @Before
    fun setup() {
        startKoin {
            modules(testModule)
        }
    }

    @After
    fun teardown() {
        stopKoin()
    }

    @Test
    fun navigationFromVideoListToVideoPlayerAndBack() {
        coEvery { getVideosUsecase() } returns videoList
        composeTestRule.setContent {
            NavigationScreen()
        }

        composeTestRule.onNodeWithText("Видео").assertIsDisplayed()

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText("Test Video").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Test Video").performClick()

        composeTestRule.onNodeWithText("Воспроизведение видео").assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription("Назад").performClick()

        composeTestRule.onNodeWithText("Видео").assertIsDisplayed()
    }
}

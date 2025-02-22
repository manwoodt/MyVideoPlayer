package com.vk.presentation
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.vk.domain.model.VideoInfo
import com.vk.domain.usecases.GetVideosUsecase
import com.vk.presentation.ui.VideoListScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import com.vk.presentation.viewmodel.VideoListViewmodel
import org.junit.After
import org.koin.core.module.dsl.viewModel
import io.mockk.coEvery
import io.mockk.mockk

class VideoListScreenTest {

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
    fun videoListIsDisplayedCorrectly() {

        coEvery { getVideosUsecase() } returns videoList
        composeTestRule.setContent {
            VideoListScreen(onVideoSelected = {})
        }

        composeTestRule.onNodeWithText("Видео").assertIsDisplayed()


        composeTestRule.onNodeWithText("Test Video").assertIsDisplayed()
        composeTestRule.onNodeWithText("Another Video").assertIsDisplayed()


        composeTestRule.onNodeWithText("Test Video").performClick()
        composeTestRule.onNodeWithText("Another Video").performClick()
    }
}
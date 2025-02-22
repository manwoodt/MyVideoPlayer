package com.vk.data

import com.vk.data.room.VideoDao
import com.vk.data.room.VideoInfoEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlinx.coroutines.flow.flowOf

class VideoDaoTest {

    @Test
    fun `insertVideos should call insert method with correct data`() = runBlocking {
        val mockVideoDao = mockk<VideoDao>(relaxed = true)
        val videos = listOf(
            VideoInfoEntity("1", "Title 1", "url1", 100, "src1"),
            VideoInfoEntity("2", "Title 2", "url2", 200, "src2")
        )

        mockVideoDao.insertVideos(videos)

        coVerify { mockVideoDao.insertVideos(videos) }
    }

    @Test
    fun `getAllVideoInfo should return Flow with correct data`() = runTest {
        // Arrange
        val mockVideoDao = mockk<VideoDao>()
        val videos = listOf(
            VideoInfoEntity("1", "Title 1", "url1", 100, "src1"),
            VideoInfoEntity("2", "Title 2", "url2", 200, "src2")
        )
        coEvery { mockVideoDao.getAllVideoInfo() } returns flowOf(videos)

        val result = mockVideoDao.getAllVideoInfo().first()

        assert(result == videos)
    }
}


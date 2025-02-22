package com.vk.data

import com.vk.data.apiService.CoverrApiService
import com.vk.data.mapper.toDomain
import com.vk.data.mapper.toDto
import com.vk.data.mapper.toEntity
import com.vk.data.model.CoverrResponse
import com.vk.data.repositoryImpl.VideoRepositoryImpl
import com.vk.data.room.VideoDao
import com.vk.data.room.VideoInfoEntity
import com.vk.domain.model.VideoInfo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response

class VideoRepositoryImplTest {

    @Test
    fun `getVideos should return videos from API and save them to DB`() = runTest {
        val mockApiService = mockk<CoverrApiService>()
        val mockVideoDao = mockk<VideoDao>(relaxed = true)

        val apiVideos = listOf(
            VideoInfo("1", "Title 1", "url1", 100, "src1"),
            VideoInfo("2", "Title 2", "url2", 200, "src2")
        )
        val apiResponse = CoverrResponse(hits = apiVideos.map { it.toDto() })
        coEvery { mockApiService.getVideos() } returns Response.success(apiResponse)

        val repository = VideoRepositoryImpl(mockApiService, mockVideoDao)

        val result = repository.getVideos()

        assert(result == apiVideos)
        coVerify { mockVideoDao.deleteAll() }
        coVerify { mockVideoDao.insertVideos(apiVideos.map { it.toEntity() }) }
    }

    @Test
    fun `getVideos should return cached videos when API fails`() = runTest {

        val mockApiService = mockk<CoverrApiService>()
        val mockVideoDao = mockk<VideoDao>()

        val cachedVideos = listOf(
            VideoInfoEntity("1", "Title 1", "url1", 100, "src1"),
            VideoInfoEntity("2", "Title 2", "url2", 200, "src2")
        )
        coEvery { mockApiService.getVideos() } throws RuntimeException("API error")
        coEvery { mockVideoDao.getAllVideoInfo() } returns flowOf(cachedVideos)

        val repository = VideoRepositoryImpl(mockApiService, mockVideoDao)

        val result = repository.getVideos()

        assert(result == cachedVideos.map { it.toDomain() })
    }

    @Test
    fun `getVideos should clear DB and return empty list when API returns empty list`() = runTest {
        val mockApiService = mockk<CoverrApiService>()
        val mockVideoDao = mockk<VideoDao>(relaxed = true)

        val apiResponse = CoverrResponse(hits = emptyList())
        coEvery { mockApiService.getVideos() } returns Response.success(apiResponse)

        val repository = VideoRepositoryImpl(mockApiService, mockVideoDao)

        val result = repository.getVideos()

        assert(result.isEmpty())
        coVerify { mockVideoDao.deleteAll() }
        coVerify { mockVideoDao.insertVideos(emptyList()) }
    }

}
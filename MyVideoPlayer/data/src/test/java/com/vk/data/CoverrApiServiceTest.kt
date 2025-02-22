package com.vk.data

import com.vk.data.apiService.CoverrApiService
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CoverrApiServiceTest {

    @Test
    fun `getVideos should call correct endpoint with query parameters`() = runBlocking {
        val mockApiService = mockk<CoverrApiService>(relaxed = true)

        mockApiService.getVideos(part = 10, query = "forest")

        coVerify { mockApiService.getVideos(part = 10, query = "forest") }
    }
}
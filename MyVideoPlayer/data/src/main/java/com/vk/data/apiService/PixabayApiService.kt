package com.vk.data.apiService

import com.vk.data.model.PixabayVideoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApiService {
    @GET("/api/videos/")
    suspend fun getVideos(
        @Query("key") apiKey: String,
        @Query("q") searchTerm: String,
    ): PixabayVideoResponse
}
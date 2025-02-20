package com.vk.data.apiService

import com.vk.data.model.YouTubeVideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApiService {
    @GET("search")
    suspend fun searchVideos(
        @Query("part") part:String = "snippet",
        @Query("q") searchTerm: String = "forest",
        @Query("type") type: String = "video"
    ): Response<YouTubeVideoResponse>
}
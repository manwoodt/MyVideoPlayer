package com.vk.data.apiService

import com.vk.data.model.CoverrResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoverrApiService {
    @GET("videos?urls=true")
    suspend fun getVideos(
        @Query("page_size") part:Int = 10,
        @Query("query") query: String = "forest",
    ): Response<CoverrResponse>

}
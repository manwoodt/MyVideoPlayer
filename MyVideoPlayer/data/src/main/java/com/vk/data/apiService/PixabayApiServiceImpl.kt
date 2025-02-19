package com.vk.data.apiService

//import com.vk.data.BuildConfig
//import com.vk.data.SecureStorage
import com.vk.data.model.PixabayVideoResponse
import retrofit2.Response
import retrofit2.Retrofit

class PixabayApiServiceImpl(
    retrofit: Retrofit
) : PixabayApiService {


    private val api = retrofit.create(PixabayApiService::class.java)

    override suspend fun getVideos(searchTerm: String): Response<PixabayVideoResponse> {
        return api.getVideos(searchTerm)
    }
}
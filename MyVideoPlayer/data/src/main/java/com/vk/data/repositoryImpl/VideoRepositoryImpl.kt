package com.vk.data.repositoryImpl

import android.util.Log
import com.vk.data.apiService.CoverrApiService
import com.vk.data.mapper.toDomain
import com.vk.domain.model.VideoInfo
import com.vk.domain.repository.VideoRepository

class VideoRepositoryImpl(
    private val apiService: CoverrApiService,
) : VideoRepository {

    override suspend fun getVideos(): List<VideoInfo> {
        val response = apiService.getVideos()
        if (response.isSuccessful) {
            val body = response.body()
            val result = body?.hits?.map { it.toDomain() } ?: emptyList()
            Log.d("VideoRepositoryImpl", result.toString())
            return result
        } else
            throw Exception("Ошибка загрузки видео: ${response.errorBody()?.string()}")
    }

}
package com.vk.data.repositoryImpl

import android.util.Log
import com.vk.data.apiService.CoverrApiService
import com.vk.data.mapper.toDomain
import com.vk.data.mapper.toEntity
import com.vk.data.room.VideoDao
import com.vk.domain.model.VideoInfo
import com.vk.domain.repository.VideoRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class VideoRepositoryImpl(
    private val apiService: CoverrApiService,
    private val videoDao: VideoDao,
) : VideoRepository {

    override suspend fun getVideos(): List<VideoInfo> {
        return try {
            val response = apiService.getVideos()
            if (response.isSuccessful) {
                val body = response.body()
                val apiVideos = body?.hits?.map { it.toDomain() } ?: emptyList()
                videoDao.deleteAll()
                videoDao.insertVideos(apiVideos.map { it.toEntity() })
                apiVideos
            } else
                throw Exception("Ошибка загрузки видео: ${response.errorBody()?.string()}")
        } catch (e: Exception) {
            Log.e("VideoRepositoryImpl", "API error: ${e.message}. Возвращаем кэш.")
            videoDao.getAllVideoInfo().first().map { it.toDomain() }
        }
    }


}
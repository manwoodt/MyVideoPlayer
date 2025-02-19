package com.vk.domain.repository

import com.vk.domain.model.VideoInfo

interface VideoRepository {
   suspend fun getVideos(): List<VideoInfo>
}
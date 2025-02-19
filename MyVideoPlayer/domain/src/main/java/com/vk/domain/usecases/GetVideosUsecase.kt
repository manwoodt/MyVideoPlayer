package com.vk.domain.usecases

import com.vk.domain.model.VideoInfo
import com.vk.domain.repository.VideoRepository

class GetVideosUsecase(private val videoRepository: VideoRepository) {
    suspend operator fun invoke():List<VideoInfo>{
      return videoRepository.getVideos()
    }
}
package com.vk.domain.model

data class VideoInfo(
    val id: Long,
    val title: String,
    val thumbnailUrl: String,
    val duration: Long,
    val srcUrl: String
)

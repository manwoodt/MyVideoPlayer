package com.vk.domain.model

data class VideoInfo(
    val id: String,
    val title: String,
    val thumbnailUrl: String,
    val duration: Int,
    val srcUrl: String
)

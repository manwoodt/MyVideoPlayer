package com.vk.data.model

data class VideoInfoDto(
    val id:Long,
    val tags: String,
    val duration: Long,
    val videos:SizeVideoDto
)


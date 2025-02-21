package com.vk.data.model


data class VideoInfoDto(
    val id:String,
    val title:String,
    val poster:String,
    val duration: String,
    val urls: UrlDto
)

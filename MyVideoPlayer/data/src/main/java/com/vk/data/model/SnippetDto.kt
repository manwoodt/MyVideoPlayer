package com.vk.data.model

data class SnippetDto(
    val title: String,
    val description: String,
    val channelTitle: String,
    val thumbnailsDto: ThumbnailsDto
)

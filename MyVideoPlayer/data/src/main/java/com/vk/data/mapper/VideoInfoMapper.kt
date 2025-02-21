package com.vk.data.mapper

import com.vk.data.model.VideoInfoDto
import com.vk.data.room.VideoInfoEntity
import com.vk.domain.model.VideoInfo

fun VideoInfo.toEntity(): VideoInfoEntity {
    return VideoInfoEntity(
        id = id,
        title = title,
        thumbnailUrl =thumbnailUrl,
        duration =duration,
        srcUrl = srcUrl
    )
}
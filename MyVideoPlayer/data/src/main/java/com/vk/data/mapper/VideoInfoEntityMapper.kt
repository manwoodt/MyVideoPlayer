package com.vk.data.mapper

import com.vk.data.room.VideoInfoEntity
import com.vk.domain.model.VideoInfo

fun VideoInfoEntity.toDomain(): VideoInfo {
    return VideoInfo(
        id = id,
        title = title,
        thumbnailUrl =thumbnailUrl,
        duration = duration,
        srcUrl = srcUrl
    )
}
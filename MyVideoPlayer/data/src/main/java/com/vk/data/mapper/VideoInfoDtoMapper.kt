package com.vk.data.mapper

import com.vk.data.model.VideoInfoDto
import com.vk.domain.model.VideoInfo


fun VideoInfoDto.toDomain(): VideoInfo {
    return VideoInfo(
        id = id,
        title = title,
        thumbnailUrl =poster,
        duration = duration.toFloat().toInt(),
        srcUrl = urls.mp4
    )
}

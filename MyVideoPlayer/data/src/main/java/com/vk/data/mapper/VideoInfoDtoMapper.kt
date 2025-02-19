package com.vk.data.mapper

import com.vk.data.model.VideoInfoDto
import com.vk.domain.model.VideoInfo

fun VideoInfoDto.toDomain():VideoInfo{
    return VideoInfo(
        id=id,
        title = tags,
        duration = duration,
        thumbnailUrl = videos.small.thumbnail,
        srcUrl = videos.small.url
    )
}
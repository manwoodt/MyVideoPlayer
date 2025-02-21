package com.vk.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videos")
data class VideoInfoEntity(
    @PrimaryKey val id: String,
    val title: String,
    val thumbnailUrl: String,
    val duration: Int,
    val srcUrl: String
)

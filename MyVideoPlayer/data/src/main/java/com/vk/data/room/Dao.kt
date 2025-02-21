package com.vk.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoDao{
    @Insert
    suspend fun insertVideos (videos: List<VideoInfoEntity>)

    @Query ("SELECT * from videos")
    fun getAllVideoInfo(): Flow<List<VideoInfoEntity>>

    @Query("DELETE FROM videos")
    suspend fun deleteAll()
}
package com.vk.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class VideoPlayerViewModel:ViewModel() {
    var isFullScreen by mutableStateOf(false)
        private set
    var videoPosition by mutableLongStateOf(0L)
        private set
    var isPlaying by mutableStateOf(true)
        private set


    fun updateFullScreen(isFullScreen:Boolean){
        this.isFullScreen = isFullScreen
    }
    fun updateVideoPosition(position: Long){
        videoPosition = position
    }
    fun updatePlayingMode(isPlaying:Boolean){
        this.isPlaying = isPlaying
    }


}
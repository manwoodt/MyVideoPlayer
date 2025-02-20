package com.vk.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vk.domain.model.VideoInfo
import com.vk.domain.usecases.GetVideosUsecase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideoListViewmodel(private val getVideosUsecase: GetVideosUsecase):ViewModel() {

    private val _videos = MutableStateFlow <List<VideoInfo>>(listOf())
    val videos: StateFlow<List<VideoInfo>> get() = _videos

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun loadVideos(){
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
               _videos.value = getVideosUsecase()
            }
            catch (e:Exception){
                _error.value = e.message
            }
            finally {
                _isLoading.value = false
            }

        }
    }

}
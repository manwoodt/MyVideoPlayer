package com.vk.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vk.domain.model.VideoInfo
import com.vk.presentation.viewmodel.VideoListViewmodel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoListScreen(onVideoSelected: (VideoInfo) -> Unit = {}) {
    val viewModel: VideoListViewmodel = koinViewModel()
    val videos by viewModel.videos.collectAsState(emptyList())
    val isLoading by viewModel.isLoading.collectAsState(false)
    val error by viewModel.error.collectAsState(null)

    LaunchedEffect(Unit) {
        viewModel.loadVideos()
    }


    Scaffold(topBar = { TopAppBar(title = {Text("Видео")}) }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            PullToRefreshBox(
                isRefreshing = isLoading,
                onRefresh = { viewModel.loadVideos() },
                modifier = Modifier.fillMaxSize()
            ) {
                VideoListContent(videos,error, onVideoSelected)
            }
        }
    }
}

@Composable
fun VideoListContent(
    videos: List<VideoInfo>,
    error: String?,
    onVideoSelected: (VideoInfo) -> Unit,
) {
    when {
        error != null -> ErrorMessage(error)
        else -> VideoList(videos, onVideoSelected)
    }
}


@Composable
fun ErrorMessage(error: String?) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "Ошибка: $error",
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
fun VideoList(videos: List<VideoInfo>, onVideoSelected: (VideoInfo) -> Unit) {
    LazyColumn {
        items(videos) { video ->
            VideoListItem(video, onClick = {onVideoSelected(video)})
        }
    }
}

package com.vk.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vk.domain.model.VideoInfo
import com.vk.presentation.viewmodel.VideoListViewmodel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material3.CircularProgressIndicator


@Composable
fun MainScreen(onVideoSelected: (VideoInfo) -> Unit = {}) {

    VideoListScreen(onVideoSelected)
}

@Composable
fun VideoListScreen(onVideoSelected: (VideoInfo) -> Unit = {}) {
    val viewModel: VideoListViewmodel = koinViewModel()
    val videos by viewModel.videos.collectAsState(emptyList())
    val isLoading by viewModel.isLoading.collectAsState(false)
    val error by viewModel.error.collectAsState(null)

    LaunchedEffect(Unit) {
        viewModel.loadVideos()

    }
    Scaffold() { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                error != null -> {
                    Text(
                        text = "Ошибка: $error",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                        items(videos) { video ->
                            VideoListItem(video, onClick = { onVideoSelected(video) })

                        }
                    }
                }
            }
        }

    }
}

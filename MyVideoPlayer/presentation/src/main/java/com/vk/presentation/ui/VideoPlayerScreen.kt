package com.vk.presentation.ui

import android.app.Activity
import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.vk.domain.model.VideoInfo
import com.vk.presentation.viewmodel.VideoPlayerViewModel
import android.content.res.Configuration
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player

@Composable
fun VideoPlayerScreen(video: VideoInfo, onBack: () -> Unit) {
    val viewModel: VideoPlayerViewModel = viewModel()
    val configuration = LocalConfiguration.current

    LaunchedEffect(configuration.orientation) {
        val shouldBeFullScreen = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        if (viewModel.isFullScreen != shouldBeFullScreen) {
            viewModel.updateFullScreen(shouldBeFullScreen)
        }
    }

    FullScreenHandler(viewModel.isFullScreen)

    Scaffold(
        topBar = {
            if (!viewModel.isFullScreen) {
                VideoPlayerTopAppBar(onBack)
            }
        },
        content = { innerPadding ->
            BackHandler(viewModel.isFullScreen) { viewModel.updateFullScreen(false) }
            VideoPlayerContent(
                video,
                innerPadding,
                viewModel
            ) { newState ->
                if (newState != viewModel.isFullScreen) {
                    viewModel.updateFullScreen(newState)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayerTopAppBar(onBack: () -> Unit) {
    TopAppBar(
        title = { Text("Воспроизведение видео") },
        navigationIcon = {
            IconButton(onClick = { onBack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Назад"
                )
            }
        }
    )
}


@Composable
fun FullScreenHandler(isFullScreen: Boolean) {
    val context = LocalContext.current
    val window = (context as? Activity)?.window ?: return

    LaunchedEffect(isFullScreen) {
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        if (isFullScreen) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            controller.hide(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars())
        } else {
            WindowCompat.setDecorFitsSystemWindows(window, true)
            controller.show(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars())
        }
    }
}

@Composable
fun VideoPlayerContent(
    video: VideoInfo,
    innerPadding: PaddingValues,
    viewModel: VideoPlayerViewModel,
    onFullScreenChange: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(Uri.parse(video.srcUrl)))
            prepare()
            seekTo(viewModel.videoPosition)
            playWhenReady = viewModel.isPlaying

            addListener(object : Player.Listener {
                override fun onPlayerError(error: PlaybackException) {
                    android.widget.Toast.makeText(
                        context,
                        "Отсутствует интернет: ${error.message}",
                        android.widget.Toast.LENGTH_LONG
                    ).show()
                    viewModel.updatePlayingMode(false)

                }
            })
        }
    }

    LaunchedEffect(viewModel.isPlaying) {
        exoPlayer.playWhenReady = viewModel.isPlaying
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.updateVideoPosition(exoPlayer.currentPosition)
            viewModel.updatePlayingMode(exoPlayer.isPlaying)
            exoPlayer.release()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    setFullscreenButtonClickListener {
                        onFullScreenChange(!viewModel.isFullScreen)
                    }
                }
            }
        )
    }
}

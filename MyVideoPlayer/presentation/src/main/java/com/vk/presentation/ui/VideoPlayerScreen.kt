package com.vk.presentation.ui

import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.exoplayer.ExoPlayer
import com.vk.domain.model.VideoInfo
import androidx.media3.common.MediaItem
import androidx.media3.ui.PlayerView

@Composable
//добавить кнопку назад
fun VideoPlayerScreen(video: VideoInfo, onBack: () -> Unit) {
    val context = LocalContext.current
    val exoPlayer = remember{
        ExoPlayer.Builder(context).build().apply{
            setMediaItem(MediaItem.fromUri(Uri.parse(video.srcUrl)))
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        }
    )

}

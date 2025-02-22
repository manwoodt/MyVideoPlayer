package com.vk.presentation.ui

import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vk.domain.model.VideoInfo
import com.vk.mylibrary.R

@Composable
fun VideoListItem(video: VideoInfo, onClick: () -> Unit) {
    Log.d("VideoListItem", "Загружаем картинку: ${video.thumbnailUrl}")
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Row(modifier = Modifier.padding(8.dp)) {
            AndroidView(
                factory = { context ->
                    ImageView(context).apply {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        Glide.with(context)
                            .load(video.thumbnailUrl)
                            .placeholder(R.drawable.ic_loading_placeholder)
                            .error(R.drawable.ic_error_placeholder)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(this)
                    }
                },
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterVertically)
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = video.title, style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Длительность: ${video.duration} сек",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
            }
        }
    }
}
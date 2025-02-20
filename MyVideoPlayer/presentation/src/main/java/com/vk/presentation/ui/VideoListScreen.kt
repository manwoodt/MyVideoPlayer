package com.vk.presentation.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vk.presentation.viewmodel.VideoListViewmodel
import org.koin.androidx.compose.koinViewModel

@Composable
fun VideoListScreen(modifier: Modifier){
    val viewModel:VideoListViewmodel = koinViewModel()


    Text(text = "Видео",  modifier = modifier)
}

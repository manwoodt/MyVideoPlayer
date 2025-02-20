package com.vk.myvideoplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vk.myvideoplayer.ui.theme.MyVideoPlayerTheme
import com.vk.presentation.ui.MainScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyVideoPlayerTheme {
                MainScreen()
            }
        }
    }
}


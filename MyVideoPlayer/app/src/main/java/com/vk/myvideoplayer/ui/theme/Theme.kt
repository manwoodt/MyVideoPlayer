package com.vk.myvideoplayer.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val BlueThemeColors  = lightColorScheme(
    primary = BluePrimary,
    secondary = BlueSecondary,
    background = BackgroundColor,
    surface = SurfaceColor,
    error = ErrorColor,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White
)

@Composable
fun MyVideoPlayerTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = BlueThemeColors,
        typography = Typography,
        content = content
    )
}
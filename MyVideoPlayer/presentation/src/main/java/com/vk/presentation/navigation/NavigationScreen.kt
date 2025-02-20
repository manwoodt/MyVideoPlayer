package com.vk.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.vk.domain.model.VideoInfo
import com.vk.presentation.ui.VideoListScreen
import com.vk.presentation.ui.VideoPlayerScreen
import java.net.URLDecoder
import java.net.URLEncoder

@Composable
fun NavigationScreen() {
    val navController = rememberNavController()
    val gson = Gson()

    NavHost(navController = navController, startDestination = "videoList") {
        composable("videoList") {
            VideoListScreen(onVideoSelected = { video ->
                val videoJson = gson.toJson(video)
                // Кодирую строку, чтобы она корректно передалась как часть URL
                val encodedVideoJson = URLEncoder.encode(videoJson, "UTF-8")
                navController.navigate("videoPlayer/$encodedVideoJson")
            })
        }
        composable(
            route = "videoPlayer/{videoJson}",
            arguments = listOf(
                androidx.navigation.navArgument("videoJson") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val encodedVideoJson = backStackEntry.arguments?.getString("videoJson")
            val videoJson = URLDecoder.decode(encodedVideoJson, "UTF-8")
            val video = gson.fromJson(videoJson, VideoInfo::class.java)
            VideoPlayerScreen(video = video, onBack = { navController.popBackStack() })
        }
    }
}

package com.vk.data.di

import com.vk.data.BuildConfig
import com.vk.data.SecureStorage
import com.vk.data.apiService.ApiKeyInterceptor
import com.vk.data.apiService.YoutubeApiService
import com.vk.data.apiService.YoutubeApiServiceImpl
import com.vk.data.repositoryImpl.VideoRepositoryImpl
import com.vk.domain.repository.VideoRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single {
        val secureStorage = SecureStorage(get())
        if (secureStorage.getApiKey()== null){
            secureStorage.saveApiKey(BuildConfig.API_KEY)
        }
        secureStorage
    }

    single {
        val secureStorage: SecureStorage = get()
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(ApiKeyInterceptor(secureStorage))
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/youtube/v3/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<YoutubeApiService> { YoutubeApiServiceImpl(get()) }

    single< VideoRepository> { VideoRepositoryImpl(get()) }

}
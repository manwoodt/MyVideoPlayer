package com.vk.data.di

import com.vk.data.SecureStorage
import com.vk.data.apiService.ApiKeyInterceptor
import com.vk.data.apiService.PixabayApiService
import com.vk.data.apiService.PixabayApiServiceImpl
import com.vk.data.repositoryImpl.VideoRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
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
            .baseUrl("https://pixabay.com")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { SecureStorage(get()) }
    single<PixabayApiService> { PixabayApiServiceImpl(get()) }
    single { VideoRepositoryImpl(get()) }

}
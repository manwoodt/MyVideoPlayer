package com.vk.data.di

import androidx.room.Room
import com.vk.data.BuildConfig
import com.vk.data.SecureStorage
import com.vk.data.apiService.ApiKeyInterceptor
import com.vk.data.apiService.CoverrApiService
import com.vk.data.repositoryImpl.VideoRepositoryImpl
import com.vk.data.room.AppDatabase
import com.vk.domain.repository.VideoRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single {
        val secureStorage = SecureStorage(get())
        val storedKey = secureStorage.getApiKey()
        val newKey = BuildConfig.API_KEY
        if (storedKey == null || storedKey != newKey) {
            secureStorage.saveApiKey(newKey)
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
            .baseUrl("https://api.coverr.co/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoverrApiService::class.java)
    }

    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "video_db")
            .fallbackToDestructiveMigration()
            .build()
    }


    single<VideoRepository> { VideoRepositoryImpl(get()) }

}
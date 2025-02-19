package com.vk.myvideoplayer

import android.app.Application
import com.vk.data.SecureStorage
import com.vk.data.di.dataModule
import com.vk.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val secureStorage = SecureStorage(this)
        if (secureStorage.getApiKey()== null){
            secureStorage.saveApiKey(BuildConfig.API_KEY)
        }

        startKoin{
        androidContext(this@MyApplication)
            modules(dataModule, domainModule)
        }
    }
}
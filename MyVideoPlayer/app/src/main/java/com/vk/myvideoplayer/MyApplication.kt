package com.vk.myvideoplayer

import android.app.Application
import com.vk.data.di.dataModule
import com.vk.domain.di.domainModule
import com.vk.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
        androidContext(this@MyApplication)
            modules(dataModule,domainModule, presentationModule)
        }
    }
}
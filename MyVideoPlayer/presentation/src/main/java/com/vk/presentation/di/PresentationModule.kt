package com.vk.presentation.di


import com.vk.presentation.viewmodel.VideoListViewmodel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { VideoListViewmodel(get()) }
}
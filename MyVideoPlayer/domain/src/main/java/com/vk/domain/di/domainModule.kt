package com.vk.domain.di

import com.vk.domain.repository.VideoRepository
import com.vk.domain.usecases.GetVideosUsecase
import org.koin.dsl.module

val domainModule = module {
    single{ GetVideosUsecase(get<VideoRepository>())}
}
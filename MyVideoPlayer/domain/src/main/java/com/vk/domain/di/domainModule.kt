package com.vk.domain.di

import com.vk.domain.usecases.GetVideosUsecase
import org.koin.dsl.module

val domainModule = module {
    factory { GetVideosUsecase(get())}
}
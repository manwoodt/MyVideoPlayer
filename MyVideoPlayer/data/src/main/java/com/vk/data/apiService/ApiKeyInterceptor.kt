package com.vk.data.apiService

import com.vk.data.SecureStorage
import okhttp3.Interceptor
import okhttp3.Response


class ApiKeyInterceptor(private val secureStorage: SecureStorage) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val apiKey = secureStorage.getApiKey() ?: ""

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("key", apiKey)
            .build()
        val newRequest = originalRequest.newBuilder().url(newUrl).build()
        return chain.proceed(newRequest)
    }
}
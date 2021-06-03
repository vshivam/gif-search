package com.search.giphy.base.networking.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiKeyInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.url

        val url = request.newBuilder()
            .addQueryParameter(API_KEY_PARAM_NAME, API_KEY)
            .build()

        val requestBuilder = original.newBuilder().url(url)

        return chain.proceed(requestBuilder.build())
    }

    private companion object {
        const val API_KEY = "GIPHY_API_KEY_GOES_HERE"
        const val API_KEY_PARAM_NAME = "api_key"
    }
}

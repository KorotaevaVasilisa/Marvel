package com.example.marvel.api

import com.example.marvel.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response

internal class MarvelApiInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newHttpUrl = request.url.newBuilder()
            .addQueryParameter("ts", Constants.TIME)
            .addQueryParameter("apikey", Constants.PUBLIC_KEY)
            .addQueryParameter("hash", Constants.hash())
            .build()
        val newRequest = request.newBuilder()
            .url(newHttpUrl)
            .build()
        return chain.proceed(newRequest)
    }
}

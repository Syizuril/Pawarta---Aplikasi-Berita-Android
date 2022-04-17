package com.syizuril.pawarta.network

import com.syizuril.pawarta.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Syekh Syihabuddin Azmil Umri on 16/04/2022.
 */
class NewsInterceptor: Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .header("Authorization", BuildConfig.API_KEY).build()
        val url = request.url.newBuilder().build()
        val original = request.newBuilder().url(url).build()
        return chain.proceed(original)
    }
}
package com.syizuril.pawarta.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.syizuril.pawarta.BuildConfig
import com.syizuril.pawarta.model.NewsModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Syekh Syihabuddin Azmil Umri on 16/04/2022.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    this.level = HttpLoggingInterceptor.Level.BODY
}

private val client = OkHttpClient.Builder()
    .addInterceptor(NewsInterceptor())
    .addInterceptor(interceptor)
    .build()

private val retrofit= Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
    .client(client)
    .baseUrl(BuildConfig.BASE_URL)
    .build()

interface NewsAPIService {
    @GET("top-headlines")
    suspend fun getTopHeadline(
        @Query("country") country: String?
    ): NewsModel

    @GET("everything")
    suspend fun getEverythingByDomain(
        @Query("domains") domains: String?
    ): NewsModel

    @GET("top-headlines")
    suspend fun getByCategory(
        @Query("country") country: String?,
        @Query("category") category: String?
    ): NewsModel
}

object NewsApi {
    val retrofitService: NewsAPIService by lazy {
        retrofit.create(NewsAPIService::class.java)
    }
}
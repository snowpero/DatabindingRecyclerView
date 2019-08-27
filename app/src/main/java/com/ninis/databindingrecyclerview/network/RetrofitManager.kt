package com.ninis.databindingrecyclerview.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitManager {
    private val TIME_OUT = 10L
    private val BASE_URL = "https://jsonplaceholder.typicode.com"

    private var okHttpClient: OkHttpClient
    private var retrofit: Retrofit

    init {
        val httpLogging = HttpLoggingInterceptor()
        httpLogging.level = HttpLoggingInterceptor.Level.BODY

        okHttpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(httpLogging)
            connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            readTimeout(TIME_OUT, TimeUnit.SECONDS)
        }.build()

        retrofit = Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        }.build()
    }

    internal fun <T> getRetrofitService(service: Class<T>): T {
        return retrofit.create(service)
    }
}
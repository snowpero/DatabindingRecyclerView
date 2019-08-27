package com.ninis.databindingrecyclerview.network

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET

interface ApiService {
    @GET("/photos")
    fun getData(): Single<ResponseBody>
}
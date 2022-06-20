package com.example.voicerecoderstt.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitObject(url:String) {
    var retrofit : Retrofit
    var retrofitService : RetrofitService

    val service : RetrofitService
        get() = retrofitService

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitService = retrofit.create(RetrofitService::class.java)
    }
}
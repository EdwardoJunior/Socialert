package com.eduardo.socialert.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    //private const val BASE_URL = "https://api.luishs.com/api/"
    private const val BASE_URL = "http://192.168.1.65:8000/api/"

    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiRegister : RegisterApiService by lazy {
        retrofit.create(RegisterApiService::class.java)
    }
}
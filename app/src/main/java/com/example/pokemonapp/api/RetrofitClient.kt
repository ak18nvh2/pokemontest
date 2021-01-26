package com.example.pokemonapp.api

import android.util.Log
import com.example.pokemonapp.commons.Utility.TIME_OUT_REQUEST_API
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val headerInterceptor = Interceptor { chain ->
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Content-type", "application/json")
            .build()
        chain.proceed(request)
    }
    private val builder = OkHttpClient.Builder()
        .readTimeout(TIME_OUT_REQUEST_API, TimeUnit.MILLISECONDS)
        .writeTimeout(TIME_OUT_REQUEST_API, TimeUnit.MILLISECONDS)
        .connectTimeout(TIME_OUT_REQUEST_API, TimeUnit.MILLISECONDS)
        .retryOnConnectionFailure(true)
        .addInterceptor(headerInterceptor)
       // .addInterceptor(logger)
        .build()
    val instance: Api by lazy {
              val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(builder)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(Api::class.java)
    }
}
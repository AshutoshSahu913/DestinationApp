package com.example.destinationapp.Retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    //before release, change this URL to your live server URL such as "https://google.com/
    private const val URL = "http://10.0.2.2:9000/"

    // Create Logger
//    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
//    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
//    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    //create okHttp client
//    private val okHttp = OkHttpClient.Builder()
    private val okHttp = OkHttpClient.Builder().addInterceptor(logger)


    //create retrofit builder
    private val builder =
        Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build())

    //create Retrofit Instance
    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}
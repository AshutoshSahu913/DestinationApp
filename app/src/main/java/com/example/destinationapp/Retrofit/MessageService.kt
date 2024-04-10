package com.example.destinationapp.Retrofit


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface MessageService {

    @GET
    fun getMessage(@Url otherUrl: String): Call<String>
}
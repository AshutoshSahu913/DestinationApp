package com.example.destinationapp.Retrofit

import com.example.destinationapp.models.Destination
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface DestinationService {

    @GET("destination/{id}")
    fun getDestination(
        @Path("id") id: Int,
        ): Call<Destination>


    //    fun getDestinationList(@Query("country") country: String): Call<List<Destination>>
//    two Query
//    fun getDestinationList(
//        @Query("country") country: String,
//        @Query("count") count: String
//    ): Call<List<Destination>>
//    if we have multiple Query then use Query map
    @Headers("x-device-type: Android", "x-faltu-bar")
    @GET("destination")
    fun getDestinationList(
        @QueryMap
        filter: HashMap<String, String>,
        @Header("Accept-Language") language: String
    ): Call<List<Destination>>


    //add data to api
    @POST("destination")
    fun addDestination(
        @Body newDestination: Destination,

        ): Call<Destination>


//    @POST("destination")
//    fun addDestination1(@Body newDestination: Destination1): Call<Destination1>

    @GET("messages")
    fun getMessage(): Call<String>


    //update data in api
    @FormUrlEncoded
    @PUT("destination/{id}")
    fun updateDestination(
        @Path("id") id: Int,
        @Field("city") city: String,
        @Field("description") desc: String,
        @Field("country") country: String,
    ): Call<Destination>

    @DELETE("destination/{id}")
    fun deleteDestination(@Path("id") id: Int): Call<Unit>
}

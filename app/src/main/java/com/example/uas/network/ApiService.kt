package com.example.uas.network

import com.example.uas.data.request.FitRequest
import com.example.uas.data.response.FitResponse
import retrofit2.Call
import com.example.uas.data.response.RegisterResponse
import com.example.uas.model.Fitness
import com.example.uas.model.Users
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("fit")
    fun getAllFits(): Call<List<Fitness>>
//
    @POST("fit")
    fun createFit(@Body request: FitRequest): Call<FitResponse>
//
    @POST("fit/{id}")
    fun updateFit(@Path("id") id: String, @Body request: FitRequest): Call<FitResponse>
//
    @DELETE("fit/{id}")
    fun deleteFit(@Path("id") id: String): Call<Void>

    @POST("account")
    fun createUser(@Body request: Users): Call<RegisterResponse>

    @GET("account")
    fun getAllUsers(): Call<List<Users>>
}
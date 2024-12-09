package com.example.tepiapp.data.api

import com.example.tepiapp.data.response.ListDetailItem
import com.example.tepiapp.data.response.ListProductItem
import com.example.tepiapp.data.response.LoginRequest
import com.example.tepiapp.data.response.LoginResponse
import com.example.tepiapp.data.response.NutriscoreRequest
import com.example.tepiapp.data.response.NutriscoreResponse
import com.example.tepiapp.data.response.RegisterRequest
import com.example.tepiapp.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<ListProductItem>

    @GET("products/{id}")
    suspend fun getDetailProducts(@Path("id") id: String): ListDetailItem

    @POST("nutriscore")
    suspend fun predict(@Body body: NutriscoreRequest): Response<NutriscoreResponse>

    @POST("login")
    suspend fun login(@Body body: LoginRequest): Response<LoginResponse>

    @POST("register")
    suspend fun register(@Body body: RegisterRequest): RegisterResponse
}
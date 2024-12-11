package com.example.tepiapp.data.api

import com.example.tepiapp.data.response.ListDetailItem
import com.example.tepiapp.data.response.ListProductItem
import com.example.tepiapp.data.response.NutriscoreRequest
import com.example.tepiapp.data.response.NutriscoreResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("products")
    fun getProducts(): Call<List<ListProductItem>>

    @GET("products/{id}")
    fun getDetailProducts(@Path("id") id: String): Call<ListDetailItem>

    @POST("nutriscore")
    fun predict(@Body body: NutriscoreRequest): Call<NutriscoreResponse>
}
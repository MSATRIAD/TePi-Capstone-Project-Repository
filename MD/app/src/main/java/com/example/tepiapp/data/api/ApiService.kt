package com.example.tepiapp.data.api

import com.example.tepiapp.data.response.ListProductItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("products")
    fun getProducts(): Call<List<ListProductItem>>
}
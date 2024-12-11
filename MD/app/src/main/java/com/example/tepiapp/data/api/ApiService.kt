package com.example.tepiapp.data.api

import android.credentials.CredentialDescription
import com.example.tepiapp.data.response.EditProfileResponse
import com.example.tepiapp.data.response.ListDetailItem
import com.example.tepiapp.data.response.ListProductItem
import com.example.tepiapp.data.response.LoginRequest
import com.example.tepiapp.data.response.LoginResponse
import com.example.tepiapp.data.response.NutriscoreRequest
import com.example.tepiapp.data.response.NutriscoreResponse
import com.example.tepiapp.data.response.ProfileResponse
import com.example.tepiapp.data.response.RegisterRequest
import com.example.tepiapp.data.response.RegisterResponse
import com.example.tepiapp.data.response.SaveRequest
import com.example.tepiapp.data.response.SaveResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @POST("saveProduct")
    suspend fun saveProduct(@Body body: SaveRequest): Response<SaveResponse>

    @GET("allSaveProduct")
    suspend fun getAllSaveProduct(): List<ListProductItem>

    @GET("saveProduct/{id}")
    suspend fun getDetailSaveProduct(@Path("id") id: String): ListDetailItem

    @GET("profile")
    suspend fun getProfile(): ProfileResponse

    @Multipart
    @PUT("editProfile")
    suspend fun editProfile(
        @Part imageFile: MultipartBody.Part? = null,
        @Part("displayName") displayName: RequestBody
    ): EditProfileResponse
}
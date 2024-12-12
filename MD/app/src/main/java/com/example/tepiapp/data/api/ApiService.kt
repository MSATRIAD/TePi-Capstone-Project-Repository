package com.example.tepiapp.data.api

import com.example.tepiapp.data.response.DeleteRequest
import com.example.tepiapp.data.response.DeleteResponse
import android.credentials.CredentialDescription
import com.example.tepiapp.data.response.EditProfileRequest
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
import com.example.tepiapp.data.response.ResetPasswordRequest
import com.example.tepiapp.data.response.ResetPasswordResponse
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

    @POST("register")
    suspend fun register(@Body body: RegisterRequest): RegisterResponse

    @POST("login")
    suspend fun login(@Body body: LoginRequest): Response<LoginResponse>

    @POST("login/resetPassword")
    suspend fun resetPassword(
        @Body request: ResetPasswordRequest
    ): ResetPasswordResponse

    @POST("products/save")
    suspend fun saveProduct(@Body body: SaveRequest): Response<SaveResponse>

    @GET("saved-products")
    suspend fun getAllSaveProduct(): List<ListProductItem>

    @GET("profile")
    suspend fun getProfile(): ProfileResponse

    @Multipart
    @PUT("profile")
    suspend fun editProfile(
        @Part imageFile: MultipartBody.Part? = null, // image file part
        @Part("displayName") displayName: RequestBody // display name as RequestBody
    ): EditProfileResponse

    @GET("saved-products/{id}")
    suspend fun getDetailSaveProduct(@Path("id") productId: String): ListDetailItem

    @DELETE("saved-products/{id}")
    suspend fun deleteSavedProduct(@Path("id") productId: String): Response<DeleteResponse>
}
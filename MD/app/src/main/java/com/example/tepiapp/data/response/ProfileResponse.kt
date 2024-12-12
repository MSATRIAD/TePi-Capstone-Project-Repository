package com.example.tepiapp.data.response

import android.media.Image
import android.net.Uri
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import java.io.File

data class ProfileResponse(
    @SerializedName("profileImage")
    val profileImage: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("displayName")
    val displayName: String,

    @SerializedName("message")
    val message: String
)

data class EditProfileRequest(
    @SerializedName("displayName")
    val displayName: String,

    @SerializedName("imageFile")
    val imageFile: Image
)

data class EditProfileResponse(
    @SerializedName("status")
    val status: String,

    @SerializedName("profileImage")
    val profileImage: String,

    @SerializedName("displayName")
    val displayName: String,

    @SerializedName("message")
    val message: String
)
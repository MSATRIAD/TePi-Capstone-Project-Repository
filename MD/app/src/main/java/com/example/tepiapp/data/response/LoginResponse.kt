package com.example.tepiapp.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("error")
    val error: Boolean,

    @SerializedName("uid")
    val uid: String,

    @SerializedName("userToken")
    val token: String
)

data class LoginRequest(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)
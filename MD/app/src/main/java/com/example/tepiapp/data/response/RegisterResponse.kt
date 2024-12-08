package com.example.tepiapp.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("error")
    val error: Boolean,

    @SerializedName("message")
    val  message: String,

    @SerializedName("uid")
    val uid: String
)

data class RegisterRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val  email: String,

    @SerializedName("password")
    val password: String
)

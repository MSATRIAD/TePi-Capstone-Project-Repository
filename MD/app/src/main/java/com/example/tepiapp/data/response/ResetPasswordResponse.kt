package com.example.tepiapp.data.response

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    val email: String
)

data class ResetPasswordResponse(
    @SerializedName("message")
    val message: String
)

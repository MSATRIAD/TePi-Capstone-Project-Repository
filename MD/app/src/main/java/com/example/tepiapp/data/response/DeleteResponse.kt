package com.example.tepiapp.data.response

import com.google.gson.annotations.SerializedName

data class DeleteResponse(
    @SerializedName("message")
    val message: String
)

data class DeleteRequest(
    @SerializedName("productId")
    val productId: String
)

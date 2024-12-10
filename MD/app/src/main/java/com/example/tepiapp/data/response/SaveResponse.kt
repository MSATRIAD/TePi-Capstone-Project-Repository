package com.example.tepiapp.data.response

import com.google.gson.annotations.SerializedName

data class SaveResponse(
    @SerializedName("message")
    val message: String
)

data class SaveRequest(
    @SerializedName("productData")
    val productData: ListDetailItem
)
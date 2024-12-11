package com.example.tepiapp.data.response

import com.google.gson.annotations.SerializedName

data class ListProductItem(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("product_name")
    val name: String,

    @field:SerializedName("grade")
    val grade: String
)
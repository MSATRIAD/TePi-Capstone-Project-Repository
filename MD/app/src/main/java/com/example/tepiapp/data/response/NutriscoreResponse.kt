package com.example.tepiapp.data.response

import com.google.gson.annotations.SerializedName

data class NutriscoreResponse(
    @SerializedName("predicted_grade")
    val predictedGrade: String
)

data class NutriscoreRequest(
    @SerializedName("energy_kcal")
    val energyKcal: Float,

    @SerializedName("sugars")
    val sugars: Float,

    @SerializedName("saturated_fat")
    val saturatedFat: Float,

    @SerializedName("salt")
    val salt: Float,

    @SerializedName("fruits_veg_nuts")
    val fruitsVegNuts: Float,

    @SerializedName("fiber")
    val fiber: Float,

    @SerializedName("proteins")
    val proteins: Float
)
package com.example.tepiapp.data.response

import com.google.gson.annotations.SerializedName

data class ListDetailItem(
    @SerializedName("energy-kcal_100g")
    val energyKcal100g: Float,

    @SerializedName("fiber_100g")
    val fiber100g: Float,

    @SerializedName("fruits-vegetables-nuts-estimate-from-ingredients_100g")
    val fruitsVegetablesNutsEstimateFromIngredients100g: Float,

    @SerializedName("nutriscore_grade")
    val nutriscoreGrade: String,

    @SerializedName("product_name")
    val productName: String,

    @SerializedName("proteins_100g")
    val proteins100g: Float,

    @SerializedName("salt_100g")
    val salt100g: Float,

    @SerializedName("saturated-fat_100g")
    val saturatedFat100g: Float,

    @SerializedName("sugars_100g")
    val sugars100g: Float
)
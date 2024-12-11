package com.example.tepiapp.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tepiapp.data.api.ApiConfig
import com.example.tepiapp.data.response.NutriscoreRequest
import com.example.tepiapp.data.response.NutriscoreResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultViewModel : ViewModel() {

    private val _nutriscoreGrade = MutableLiveData<String>()
    val nutriscoreGrade: LiveData<String> get() = _nutriscoreGrade

    fun getNutriscoreGrade(
        energyKcal: Float,
        sugars: Float,
        saturatedFat: Float,
        salt: Float,
        fruitsVegNuts: Float,
        fiber: Float,
        proteins: Float
    ) {
        val apiService = ApiConfig.getApiService()
        val request = NutriscoreRequest(
            energyKcal = energyKcal,
            sugars = sugars,
            saturatedFat = saturatedFat,
            salt = salt,
            fruitsVegNuts = fruitsVegNuts,
            fiber = fiber,
            proteins = proteins
        )

        apiService.predict(request).enqueue(object : Callback<NutriscoreResponse> {
            override fun onResponse(call: Call<NutriscoreResponse>, response: Response<NutriscoreResponse>) {
                if (response.isSuccessful) {
                    val grade = response.body()?.predictedGrade ?: "Unknown"
                    _nutriscoreGrade.value = grade
                } else {
                    _nutriscoreGrade.value = "Error"
                }
            }

            override fun onFailure(call: Call<NutriscoreResponse>, t: Throwable) {
                _nutriscoreGrade.value = "Error"
            }
        })
    }
}

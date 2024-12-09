package com.example.tepiapp.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tepiapp.data.UserRepository
import com.example.tepiapp.data.api.ApiConfig
import com.example.tepiapp.data.response.NutriscoreRequest
import com.example.tepiapp.data.response.NutriscoreResponse
import kotlinx.coroutines.launch

class ResultViewModel : ViewModel() {

    private lateinit var userRepository: UserRepository

    private val _nutriscoreGrade = MutableLiveData<String>()
    val nutriscoreGrade: LiveData<String> get() = _nutriscoreGrade

    // Set UserRepository
    fun setUserRepository(userRepository: UserRepository) {
        this.userRepository = userRepository
    }

    // Fungsi untuk melakukan prediksi Nutriscore
    fun predictNutriscore(
        energyKcal: Float,
        sugars: Float,
        saturatedFat: Float,
        salt: Float,
        fruitsVegNuts: Float,
        fiber: Float,
        proteins: Float
    ) {
        val request = NutriscoreRequest(
            energyKcal = energyKcal,
            sugars = sugars,
            saturatedFat = saturatedFat,
            salt = salt,
            fruitsVegNuts = fruitsVegNuts,
            fiber = fiber,
            proteins = proteins
        )

        // Panggil fungsi predictNutriscore dari UserRepository
        viewModelScope.launch {
            try {
                val response = userRepository.predictNutriscore(request)
                _nutriscoreGrade.value = response.predictedGrade ?: "Unknown"
            } catch (e: Exception) {
                _nutriscoreGrade.value = "Error"
            }
        }
    }
}

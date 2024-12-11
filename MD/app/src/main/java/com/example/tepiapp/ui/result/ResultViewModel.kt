package com.example.tepiapp.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tepiapp.data.UserRepository
import com.example.tepiapp.data.api.ApiConfig
import com.example.tepiapp.data.response.NutriscoreRequest
import com.example.tepiapp.data.response.NutriscoreResponse
import com.example.tepiapp.data.response.SaveRequest
import kotlinx.coroutines.launch

class ResultViewModel : ViewModel() {

    private lateinit var userRepository: UserRepository

    private val _nutriscoreGrade = MutableLiveData<String>()
    val nutriscoreGrade: LiveData<String> get() = _nutriscoreGrade

    private val _saveStatus = MutableLiveData<String>()
    val saveStatus: LiveData<String> get() = _saveStatus

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
                _nutriscoreGrade.value = response.body()?.predictedGrade ?: "Unknown"
            } catch (e: Exception) {
                _nutriscoreGrade.value = "Error"
            }
        }
    }

    fun saveProduct(data: SaveRequest) {
        viewModelScope.launch {
            try {
                val response = userRepository.saveProduct(data)
                if (response.isSuccessful) {
                    _saveStatus.value = response.body()?.message
                } else {
                    _saveStatus.value = "Failed to save product"
                }
            } catch (e: Exception) {
                _saveStatus.value = "Error: ${e.message}"
            }
        }
    }

    fun deleteSavedProduct(productId: String) {
        viewModelScope.launch {
            try {
                val response = userRepository.deleteSavedProduct(productId)
                if (response.isSuccessful) {
                    _saveStatus.postValue("Product deleted successfully")
                } else {
                    _saveStatus.postValue("Failed to delete product: ${response.message()}")
                }
            } catch (e: Exception) {
                _saveStatus.postValue("Error: ${e.message}")
            }
        }
    }
}

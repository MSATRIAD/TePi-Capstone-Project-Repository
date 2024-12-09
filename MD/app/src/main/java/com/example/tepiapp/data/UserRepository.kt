package com.example.tepiapp.data

import com.example.tepiapp.data.api.ApiService
import com.example.tepiapp.data.pref.UserModel
import com.example.tepiapp.data.pref.UserPreference
import com.example.tepiapp.data.response.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import retrofit2.Call
import retrofit2.Response

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        val request = RegisterRequest(name, email, password)
        return apiService.register(request)
    }

    suspend fun login(email: String, password: String): Response<LoginResponse> {
        val request = LoginRequest(email, password)

        val response = apiService.login(request)

        if (response.isSuccessful) {
            val loginResponse = response.body()
            if (loginResponse != null && !loginResponse.error) {
                val user = UserModel(
                    email = email,
                    token = loginResponse.token,
                    isLogin = true
                )
                saveSession(user)
            }
        }
        return response
    }

    fun getProducts(): List<ListProductItem> {
        val response = apiService.getProducts().execute()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Failed to fetch products: ${response.code()} - ${response.message()}")
        }
    }

    // Fungsi untuk mendapatkan detail produk berdasarkan ID
    fun getDetailProducts(id: String): ListDetailItem {
        val response = apiService.getDetailProducts(id).execute()
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Product details not found")
        } else {
            throw Exception("Failed to fetch product details: ${response.code()} - ${response.message()}")
        }
    }

    // Fungsi untuk melakukan prediksi Nutriscore
    fun predictNutriscore(data: NutriscoreRequest): NutriscoreResponse {
        val response = apiService.predict(data).execute()
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Prediction failed")
        } else {
            throw Exception("Error: ${response.code()} - ${response.message()}")
        }
    }

    // Fungsi untuk menyimpan sesi pengguna
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    // Fungsi untuk mendapatkan sesi pengguna
    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    // Fungsi logout
    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}

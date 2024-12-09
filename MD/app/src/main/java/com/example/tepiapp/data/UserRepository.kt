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

    suspend fun getProducts(): List<ListProductItem> {
        return try {
            apiService.getProducts()
        } catch (e: Exception) {
            throw Exception("Failed to fetch products: ${e.message}")
        }
    }

    suspend fun predictNutriscore(data: NutriscoreRequest): Response<NutriscoreResponse> {
        return try {
            apiService.predict(data)
        } catch (e: Exception) {
            throw Exception("Prediction failed: ${e.message}", e)
        }
    }

    suspend fun getDetailProducts(id: String): ListDetailItem {
        return try {
            apiService.getDetailProducts(id)
        } catch (e: Exception) {
            throw Exception("Failed to fetch product details: ${e.message}", e)
        }
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

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

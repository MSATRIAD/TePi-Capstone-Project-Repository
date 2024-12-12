package com.example.tepiapp.data

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import com.example.tepiapp.data.api.ApiService
import com.example.tepiapp.data.pref.UserModel
import com.example.tepiapp.data.pref.UserPreference
import com.example.tepiapp.data.response.*
import com.google.android.gms.common.util.IOUtils.copyStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File

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

    suspend fun resetPassword(email: String): ResetPasswordResponse {
        val request = ResetPasswordRequest(email)
        return apiService.resetPassword(request)
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

    suspend fun saveProduct(data: SaveRequest): Response<SaveResponse> {
        return try {
            apiService.saveProduct(data)
        } catch (e: Exception) {
            throw Exception("Failed to save data: ${e.message}", e)
        }
    }

    suspend fun deleteSavedProduct(productId: String): Response<DeleteResponse> {
        return try {
            apiService.deleteSavedProduct(productId)
        } catch (e: Exception) {
            throw Exception("Failed to fetch delete product: ${e.message}", e)
        }
    }

    suspend fun getAllSaveProduct(): List<ListProductItem> {
        return try {
            apiService.getAllSaveProduct()
        } catch (e: Exception) {
            throw Exception("Failed to fetch save product: ${e.message}", e)
        }
    }

    suspend fun getDetailSaveProduct(productId: String): ListDetailItem {
        return try {
            apiService.getDetailSaveProduct(productId)
        } catch (e: Exception) {
            throw Exception("Failed to fetch product details: ${e.message}", e)
        }
    }

    suspend fun getProfile(): ProfileResponse {
        return try {
            apiService.getProfile()
        } catch (e: Exception) {
            throw Exception("Failed to get profile info: ${e.message}", e)
        }
    }

    suspend fun editProfile(displayName: String, imageUri: Uri?, context: Context): EditProfileResponse {
        return withContext(Dispatchers.IO) {
            try {
                // Convert the displayName to RequestBody
                val displayNameRequestBody = displayName.toRequestBody("text/plain".toMediaTypeOrNull())

                // Create MultipartBody.Part for the image file (if available)
                val imageFilePart = imageUri?.let { createMultipartBody(context, it) }

                // Call the API with the imageFile and displayName
                return@withContext apiService.editProfile(imageFilePart, displayNameRequestBody)
            } catch (e: Exception) {
                throw Exception("Failed to edit profile: ${e.message}", e)
            }
        }
    }

    private fun createMultipartBody(context: Context, uri: Uri): MultipartBody.Part? {
        return try {
            val contentResolver: ContentResolver = context.contentResolver
            val file = File(uri.path ?: "")

            if (file.length() > 5 * 1024 * 1024) {
                throw Exception("Image size should be less than 5MB")
            }

            // Create a temporary file and copy the content of the image
            val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)

            contentResolver.openInputStream(uri).use { inputStream ->
                tempFile.outputStream().use { outputStream ->
                    copyStream(inputStream!!, outputStream)
                }
            }

            val requestFile = tempFile.asRequestBody("image/jpg".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("imageFile", tempFile.name, requestFile)
        } catch (e: Exception) {
            e.printStackTrace()
            null
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
        fun getInstance(userPreferences: UserPreference, apiService: ApiService) =
            UserRepository(userPreferences, apiService)
    }
}
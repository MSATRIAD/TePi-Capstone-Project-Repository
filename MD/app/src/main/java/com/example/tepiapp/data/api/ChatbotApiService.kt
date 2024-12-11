package com.example.tepiapp.data.api

import retrofit2.http.Body
import retrofit2.http.POST

interface ChatbotApiService {
    @POST("generate-response")
    suspend fun sendProductDetails(@Body productDetails: Map<String, String>): ChatbotResponse

    @POST("generate-response")
    suspend fun sendCustomPrompt(@Body customPrompt: Map<String, String>): ChatbotResponse
}

data class ChatbotResponse(
    val input: String,
    val response: String,
    val session_id: String
)
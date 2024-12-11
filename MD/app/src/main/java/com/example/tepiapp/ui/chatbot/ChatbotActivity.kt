package com.example.tepiapp.ui.chatbot

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tepiapp.data.adapter.ChatHistoryAdapter
import com.example.tepiapp.data.adapter.SuggestionAdapter
import com.example.tepiapp.databinding.ActivityChatbotBinding
import androidx.lifecycle.lifecycleScope
import com.example.tepiapp.data.api.ApiConfig
import com.example.tepiapp.data.pref.Message
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class ChatbotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatbotBinding
    private lateinit var chatAdapter: ChatHistoryAdapter
    private var sessionId: String? = null

    private var energyKcal: Float = 0.0f
    private var sugars: Float = 0.0f
    private var saturatedFat: Float = 0.0f
    private var salt: Float = 0.0f
    private var fruitsVegNuts: Float = 0.0f
    private var fiber: Float = 0.0f
    private var proteins: Float = 0.0f
    private var nutriscore: String = ""
    private var productName: String = ""

    private val suggestions = listOf(
        "Mengapa minuman ini memiliki grade tersebut?",
        "Jelaskan komposisi dari minuman ini!"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productId = intent.getStringExtra("id") ?: "default"
        productName = intent.getStringExtra("product_name") ?: "product name"
        energyKcal = intent.getFloatExtra("energy_kcal", 0.0f)
        sugars = intent.getFloatExtra("sugars", 0.0f)
        saturatedFat = intent.getFloatExtra("saturated_fat", 0.0f)
        salt = intent.getFloatExtra("salt", 0.0f)
        fruitsVegNuts = intent.getFloatExtra("fruits_veg_nuts", 0.0f)
        fiber = intent.getFloatExtra("fiber", 0.0f)
        proteins = intent.getFloatExtra("proteins", 0.0f)
        nutriscore = intent.getStringExtra("nutriscore_grade") ?: "None"

        // Initialize RecyclerView and Chat Adapter
        initRecyclerView()
        loadChatHistory(productId)

        // Set up send button
        binding.ibSend.setOnClickListener {
            val question = binding.tietQuestion.text.toString()
            if (question.isNotEmpty()) {
                sendMessage(question, productId)
            } else {
                Toast.makeText(this, "Harap masukkan pertanyaan.", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up question suggestions
        binding.rvQuestionSuggestions.apply {
            layoutManager = LinearLayoutManager(this@ChatbotActivity, LinearLayoutManager.VERTICAL, false)
            adapter = SuggestionAdapter(suggestions) { selectedSuggestion ->
                binding.tietQuestion.setText(selectedSuggestion)
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initRecyclerView() {
        chatAdapter = ChatHistoryAdapter()
        binding.rvChatHistory.apply {
            layoutManager = LinearLayoutManager(this@ChatbotActivity)
            adapter = chatAdapter
        }
    }

    private fun sendMessage(question: String, productId: String) {

        chatAdapter.addMessage(Message(question, true))
        binding.tietQuestion.text?.clear()

        binding.progressBar.visibility = View.VISIBLE

//        val response = "Ini adalah jawaban untuk pertanyaan: $question"

        lifecycleScope.launch {
            try {
                val response = if (suggestions.contains(question)) {
                    ApiConfig.getChatbotService().sendProductDetails(
                        mapOf(
                            "product_name" to productName,
                            "energy_kcal" to energyKcal.toString(),
                            "sugars" to sugars.toString(),
                            "saturated_fat" to saturatedFat.toString(),
                            "salt" to salt.toString(),
                            "fruits_veg_nuts" to fruitsVegNuts.toString(),
                            "fiber" to fiber.toString(),
                            "proteins" to proteins.toString(),
                            "nutriscore_grade" to nutriscore,
                            "session_id" to (sessionId ?: "")
                        )
                    )
                } else {
                    ApiConfig.getChatbotService().sendCustomPrompt(
                        mapOf(
                            "custom_prompt" to question,
                            "session_id" to (sessionId ?: "")
                        )
                    )
                }

                sessionId = response.session_id
                chatAdapter.addMessage(Message(response.response, false))
                saveChatHistory(productId)
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is java.net.UnknownHostException -> "Tidak ada koneksi internet."
                    is retrofit2.HttpException -> "Server error: ${e.code()} ${e.message()}"
                    else -> "Gagal mengirim pesan: ${e.message}"
                }
                Toast.makeText(this@ChatbotActivity, errorMessage, Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
                scrollToBottom()
            }
        }
    }

    private fun scrollToBottom() {
        binding.rvChatHistory.apply {
            if ((layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() == chatAdapter.itemCount - 2) {
                scrollToPosition(chatAdapter.itemCount - 1)
            }
        }
    }

    private fun saveChatHistory(productId: String) {
        val sharedPreferences = getSharedPreferences("ChatHistory", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val chatHistoryJson = Gson().toJson(chatAdapter.getMessages())
        editor.putString("$productId-chat_history", chatHistoryJson)
        editor.putString("$productId-session_id", sessionId)
        editor.apply()
    }

    private fun loadChatHistory(productId: String) {
        val sharedPreferences = getSharedPreferences("ChatHistory", MODE_PRIVATE)
        val chatHistoryJson = sharedPreferences.getString("$productId-chat_history", null)
        sessionId = sharedPreferences.getString("$productId-session_id", null)
        if (chatHistoryJson != null) {
            val messages = Gson().fromJson(chatHistoryJson, Array<Message>::class.java).toList()
            chatAdapter.setMessages(messages)
        }
    }
}
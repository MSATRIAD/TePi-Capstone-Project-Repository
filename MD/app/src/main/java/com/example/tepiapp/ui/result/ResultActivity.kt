package com.example.tepiapp.ui.result

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tepiapp.R
import com.example.tepiapp.data.UserRepository
import com.example.tepiapp.data.api.ApiConfig
import com.example.tepiapp.data.pref.UserPreference
import com.example.tepiapp.data.pref.dataStore
import com.example.tepiapp.data.response.ListDetailItem
import com.example.tepiapp.data.response.SaveRequest
import com.example.tepiapp.databinding.ActivityResultBinding
import com.example.tepiapp.ui.chatbot.ChatbotActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private val viewModel: ResultViewModel by viewModels()
    private var isBookmarked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productName = intent.getStringExtra("product_name") ?: "Unknown Product"
        val energyKcal = intent.getFloatExtra("energy_kcal", 0f)
        val sugars = intent.getFloatExtra("sugars", 0f)
        val saturatedFat = intent.getFloatExtra("saturated_fat", 0f)
        val salt = intent.getFloatExtra("salt", 0f)
        val fruitsVegNuts = intent.getFloatExtra("fruits_veg_nuts", 0f)
        val fiber = intent.getFloatExtra("fiber", 0f)
        val proteins = intent.getFloatExtra("proteins", 0f)

        binding.tvProductName.text = productName
        binding.tvEnergyKcal.text = "$energyKcal kcal"
        binding.tvSugars.text = "$sugars g"
        binding.tvSaturatedFat.text = "$saturatedFat g"
        binding.tvSalt.text = "$salt g"
        binding.tvFruitsVegetables.text = "$fruitsVegNuts g"
        binding.tvFiber.text = "$fiber g"
        binding.tvProteins.text = "$proteins g"

        lifecycleScope.launch {
            val userPreference = UserPreference.getInstance(this@ResultActivity.dataStore)
            val token = userPreference.getSession().first().token
            val apiService = ApiConfig.getApiService(token)

            val userRepository = UserRepository.getInstance(userPreference, apiService)
            viewModel.setUserRepository(userRepository)

            showProgressBar(true)

            viewModel.predictNutriscore(
                energyKcal, sugars, saturatedFat, salt, fruitsVegNuts, fiber, proteins
            )
        }

        viewModel.nutriscoreGrade.observe(this) { grade ->
            showProgressBar(false)
            binding.tvNutriscoreGrade.text = getString(R.string.nutriscore_grade, grade.uppercase())
        }

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }

        updateBookmarkIcon()

        binding.fabSaveProduct.setOnClickListener {
            if (isBookmarked) {
                deleteSavedProduct()
            } else {
                saveProduct(
                    productName, energyKcal, sugars, saturatedFat, salt,
                    fruitsVegNuts, fiber, proteins, binding.tvNutriscoreGrade.text.toString()
                )
                isBookmarked = true
                updateBookmarkIcon()
            }
        }

        viewModel.saveStatus.observe(this) { status ->
            if (status == "Success") {
                toggleBookmarkState()
            }
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
        }

        binding.fabChatbot.setOnClickListener {
            val intent = Intent(this, ChatbotActivity::class.java).apply {
                putExtra("product_name", productName)
                putExtra("energy_kcal", energyKcal)
                putExtra("sugars", sugars)
                putExtra("saturated_fat", saturatedFat)
                putExtra("salt", salt)
                putExtra("fruits_veg_nuts", fruitsVegNuts)
                putExtra("fiber", fiber)
                putExtra("proteins", proteins)
                putExtra("nutriscore_grade", binding.tvNutriscoreGrade.text.toString())
            }
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        // Refresh UI when activity is resumed
        updateBookmarkIcon()  // Ensure the bookmark icon is up-to-date
    }

    private fun saveProduct(
        productName: String, energyKcal: Float, sugars: Float,
        saturatedFat: Float, salt: Float, fruitsVegNuts: Float, fiber: Float,
        proteins: Float, nutriscoreGrade: String
    ) {
        val productData = ListDetailItem(
            productName = productName,
            energyKcal100g = energyKcal,
            sugars100g = sugars,
            saturatedFat100g = saturatedFat,
            salt100g = salt,
            fruitsVegetablesNutsEstimateFromIngredients100g = fruitsVegNuts,
            fiber100g = fiber,
            proteins100g = proteins,
            nutriscoreGrade = nutriscoreGrade
        )
        val saveRequest = SaveRequest(productData)

        viewModel.saveProduct(saveRequest)

        viewModel.saveStatus.observe(this) { status ->
            if (status == "Success") {
                isBookmarked = true
                updateBookmarkIcon()
                Toast.makeText(this, "Product saved successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to save product: $status", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteSavedProduct() {
        val productId = intent.getStringExtra("productId") ?: run {
            Toast.makeText(this, "Product ID is missing", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            viewModel.deleteSavedProduct(productId)
            viewModel.deleteSaveStatus.observe(this@ResultActivity) { status ->
                if (status == "Product deleted successfully") {
                    isBookmarked = intent.getBooleanExtra("isBookmarked", true)
                    updateBookmarkIcon()
                    Toast.makeText(this@ResultActivity, status, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ResultActivity, "Failed to delete product: $status", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun toggleBookmarkState() {
        isBookmarked = !isBookmarked
        updateBookmarkIcon()
    }

    private fun updateBookmarkIcon() {
        if (isBookmarked) {
            binding.fabSaveProduct.setImageResource(R.drawable.baseline_bookmark_24)
        } else {
            binding.fabSaveProduct.setImageResource(R.drawable.baseline_bookmark_border_24)
        }
    }

    private fun showProgressBar(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }
}

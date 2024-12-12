package com.example.tepiapp.ui.result

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

        val productName = intent.getStringExtra("productName") ?: "Unknown Product"
        val energyKcal = intent.getFloatExtra("energyKcal", 0f)
        val sugars = intent.getFloatExtra("sugars", 0f)
        val saturatedFat = intent.getFloatExtra("saturatedFat", 0f)
        val salt = intent.getFloatExtra("salt", 0f)
        val fruitsVegNuts = intent.getFloatExtra("fruitsVegNuts", 0f)
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
            try {
                viewModel.deleteSavedProduct(productId)

                isBookmarked = false
                updateBookmarkIcon()

                Toast.makeText(this@ResultActivity, "Product deleted successfully", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Toast.makeText(this@ResultActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
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

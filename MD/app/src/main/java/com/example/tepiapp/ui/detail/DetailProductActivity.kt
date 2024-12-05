package com.example.tepiapp.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.tepiapp.R
import com.example.tepiapp.data.api.ApiConfig
import com.example.tepiapp.data.response.ListDetailItem
import com.example.tepiapp.databinding.ActivityDetailBinding

class DetailProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiService = ApiConfig.getApiService()
        viewModel.setApiService(apiService)

        val productId = intent.getStringExtra("productId")

        if (productId.isNullOrEmpty()) {
            Toast.makeText(this, "Invalid product ID", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.productDetail.observe(this) { response ->
            if (response != null) {
                updateUI(response)
            } else {
                Toast.makeText(this, "Error fetching product details", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.getProductDetails(productId)

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) android.view.View.VISIBLE else android.view.View.GONE
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun updateUI(detail: ListDetailItem) {
        binding.tvProductName.text = detail.productName
        binding.tvEnergyKcal.text = getString(R.string.energy_kcal, detail.energyKcal100g)
        binding.tvFiber.text = getString(R.string.fiber, detail.fiber100g)
        binding.tvFruitsVegetables.text = getString(R.string.fruits_vegetables, detail.fruitsVegetablesNutsEstimateFromIngredients100g)
        binding.tvNutriscoreGrade.text = getString(R.string.nutriscore_grade, detail.nutriscoreGrade)
        binding.tvProteins.text = getString(R.string.proteins, detail.proteins100g)
        binding.tvSalt.text = getString(R.string.salt, detail.salt100g)
        binding.tvSaturatedFat.text = getString(R.string.saturated_fat, detail.saturatedFat100g)
        binding.tvSugars.text = getString(R.string.sugars, detail.sugars100g)
    }
}
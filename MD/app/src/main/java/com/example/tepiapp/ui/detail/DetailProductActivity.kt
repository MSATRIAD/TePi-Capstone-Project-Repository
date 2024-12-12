package com.example.tepiapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tepiapp.R
import com.example.tepiapp.data.UserRepository
import com.example.tepiapp.databinding.ActivityDetailBinding
import com.example.tepiapp.di.Injection
import com.example.tepiapp.data.response.ListDetailItem
import com.example.tepiapp.ui.chatbot.ChatbotActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetailProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productId = intent.getStringExtra("productId")

        if (productId.isNullOrEmpty()) {
            Toast.makeText(this, "Invalid product ID", Toast.LENGTH_SHORT).show()
            return
        }

        // Initialize UserRepository using Injection
        val userRepository = Injection.provideRepository(this)
        viewModel.setUserRepository(userRepository)

        // Use lifecycleScope to call suspend function
        lifecycleScope.launch {
            viewModel.getProductDetails(productId)
        }

        // Observe product details
        viewModel.productDetail.observe(this) { response ->
            if (response != null) {
                updateUI(response)
            } else {
                Toast.makeText(this, "Error fetching product details", Toast.LENGTH_SHORT).show()
            }
        }

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

        binding.fabChatbot.setOnClickListener {
            val productDetails = viewModel.productDetail.value
            if (productDetails != null) {
                val intent = Intent(this, ChatbotActivity::class.java).apply {
                    putExtra("product_name", productDetails.productName)
                    putExtra("energy_kcal", productDetails.energyKcal100g)
                    putExtra("sugars", productDetails.sugars100g)
                    putExtra("saturatedFat", productDetails.saturatedFat100g)
                    putExtra("salt", productDetails.salt100g)
                    putExtra("fruits_veg_nuts", productDetails.fruitsVegetablesNutsEstimateFromIngredients100g)
                    putExtra("fiber", productDetails.fiber100g)
                    putExtra("proteins", productDetails.proteins100g)
                    putExtra("nutriscore_grade", productDetails.nutriscoreGrade)
                    putExtra("id", productId)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Data produk tidak tersedia", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(detail: ListDetailItem) {
        binding.tvProductName.text = detail.productName

        binding.tvEnergyKcal.text = getString(R.string.energy_kcal, detail.energyKcal100g)
        binding.tvFiber.text = getString(R.string.fiber, detail.fiber100g)
        binding.tvFruitsVegetables.text = getString(R.string.fruits_vegetables, detail.fruitsVegetablesNutsEstimateFromIngredients100g)
        binding.tvNutriscoreGrade.text = getString(R.string.nutriscore_grade, detail.nutriscoreGrade.uppercase())
        binding.tvProteins.text = getString(R.string.proteins, detail.proteins100g)
        binding.tvSalt.text = getString(R.string.salt, detail.salt100g)
        binding.tvSaturatedFat.text = getString(R.string.saturated_fat, detail.saturatedFat100g)
        binding.tvSugars.text = getString(R.string.sugars, detail.sugars100g)
    }
}

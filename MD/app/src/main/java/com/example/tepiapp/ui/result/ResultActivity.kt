package com.example.tepiapp.ui.result

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data yang dikirim dari ScanFragment
        val productName = intent.getStringExtra("productName") ?: "Unknown Product"
        val energyKcal = intent.getFloatExtra("energyKcal", 0f)
        val sugars = intent.getFloatExtra("sugars", 0f)
        val saturatedFat = intent.getFloatExtra("saturatedFat", 0f)
        val salt = intent.getFloatExtra("salt", 0f)
        val fruitsVegNuts = intent.getFloatExtra("fruitsVegNuts", 0f)
        val fiber = intent.getFloatExtra("fiber", 0f)
        val proteins = intent.getFloatExtra("proteins", 0f)

        // Menampilkan nama produk di UI
        binding.tvProductName.text = productName
        binding.tvEnergyKcal.text = "$energyKcal kcal"
        binding.tvSugars.text = "$sugars g"
        binding.tvSaturatedFat.text = "$saturatedFat g"
        binding.tvSalt.text = "$salt g"
        binding.tvFruitsVegetables.text = "$fruitsVegNuts%"
        binding.tvFiber.text = "$fiber g"
        binding.tvProteins.text = "$proteins g"

        // Memproses data menggunakan ResultViewModel
        lifecycleScope.launch {
            val userPreference = UserPreference.getInstance(this@ResultActivity.dataStore)
            val token = userPreference.getSession().first().token
            val apiService = ApiConfig.getApiService(token)

            val userRepository = UserRepository.getInstance(userPreference, apiService)
            viewModel.setUserRepository(userRepository)

            // Memanggil fungsi prediksi Nutriscore dari ViewModel
            viewModel.predictNutriscore(
                energyKcal, sugars, saturatedFat, salt, fruitsVegNuts, fiber, proteins
            )
        }

        // Observasi hasil grade Nutriscore
        viewModel.nutriscoreGrade.observe(this) { grade ->
            binding.tvNutriscoreGrade.text = getString(R.string.nutriscore_grade, grade.uppercase())
        }

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }

        binding.fabSaveProduct.setOnClickListener {
            // Buat objek SaveRequest
            val productData = ListDetailItem(
                productName = productName,
                energyKcal100g = energyKcal,
                sugars100g = sugars,
                saturatedFat100g = saturatedFat,
                salt100g = salt,
                fruitsVegetablesNutsEstimateFromIngredients100g = fruitsVegNuts,
                fiber100g = fiber,
                proteins100g = proteins,
                nutriscoreGrade = binding.tvNutriscoreGrade.text.toString()
            )
            val saveRequest = SaveRequest(productData)

            // Panggil ViewModel untuk menyimpan data
            viewModel.saveProduct(saveRequest)
        }

        viewModel.saveStatus.observe(this) { status ->
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
        }
    }
}

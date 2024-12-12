package com.example.tepiapp.ui.predict

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tepiapp.data.api.ApiConfig
import com.example.tepiapp.data.response.NutriscoreRequest
import com.example.tepiapp.databinding.FragmentPredictBinding
import com.example.tepiapp.ui.result.ResultActivity
import kotlinx.coroutines.launch

class PredictFragment : Fragment() {
    private var _binding: FragmentPredictBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PredictViewModel by viewModels()

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View {
        _binding = FragmentPredictBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnSubmitData.setOnClickListener { submitData() }
    }

    private fun submitData() {
        // Ambil nama produk yang dimasukkan oleh pengguna
        val productName = binding.etProductName.text.toString().trim()
        if (productName.isEmpty()) {
            Toast.makeText(requireContext(), "Nama produk harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        val energyKcal = binding.etTotalEnergy.text.toString().toFloatOrNull()
        val sugars = binding.etSugar.text.toString().toFloatOrNull()
        val saturatedFat = binding.etFat.text.toString().toFloatOrNull()
        val salt = binding.etSalt.text.toString().toFloatOrNull()
        val fruitsVegNuts = binding.etFruitVegNut.text.toString().toFloatOrNull()
        val fiber = binding.etFiber.text.toString().toFloatOrNull()
        val proteins = binding.etProtein.text.toString().toFloatOrNull()

        if (listOf(
                energyKcal,
                sugars,
                saturatedFat,
                salt,
                fruitsVegNuts,
                fiber,
                proteins
            ).any { it == null }
        ) {
            // Tampilkan pesan error jika ada input yang kosong
            Toast.makeText(
                requireContext(),
                "Semua field harus diisi dengan benar",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Fetch token from shared preferences or repository
        val token = "your_token_here" // Replace with actual token retrieval method

        // Pass the token to the API service
        val apiService = ApiConfig.getApiService(token)
        val request = NutriscoreRequest(
            energyKcal = energyKcal!!,
            sugars = sugars!!,
            saturatedFat = saturatedFat!!,
            salt = salt!!,
            fruitsVegNuts = fruitsVegNuts!!,
            fiber = fiber!!,
            proteins = proteins!!
        )
        lifecycleScope.launch {
            try {
                val response = apiService.predict(request) // This is a suspend function call
                if (response != null) {
                    val intent = Intent(requireContext(), ResultActivity::class.java).apply {
                        putExtra("productName", productName) // Kirimkan nama produk
                        putExtra("energyKcal", energyKcal)
                        putExtra("sugars", sugars)
                        putExtra("saturatedFat", saturatedFat)
                        putExtra("salt", salt)
                        putExtra("fruitsVegNuts", fruitsVegNuts)
                        putExtra("fiber", fiber)
                        putExtra("proteins", proteins)
                    }
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Gagal mendapatkan prediksi",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

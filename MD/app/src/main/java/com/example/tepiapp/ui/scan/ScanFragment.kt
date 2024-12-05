package com.example.tepiapp.ui.scan

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tepiapp.data.api.ApiConfig
import com.example.tepiapp.data.response.NutriscoreRequest
import com.example.tepiapp.data.response.NutriscoreResponse
import com.example.tepiapp.databinding.FragmentScanBinding
import getImageUri
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScanFragment : Fragment() {
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ScanViewModel by viewModels()

    private var currentImageUri: Uri? = null

    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startCamera()
        } else {
            // Handle the case when the user denies the permission
            Log.e("Permission", "Camera permission denied")
        }
    }

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.btnOpenCamera.setOnClickListener { checkCameraPermission() }
        binding.btnSubmitData.setOnClickListener { submitData() }
    }

    private fun observeViewModel() {
        viewModel.imageUri.observe(viewLifecycleOwner) { uri ->
            uri?.let {
                currentImageUri = it
                binding.ivCapturedImage.setImageURI(uri)
            }
        }
    }

    private fun checkCameraPermission() {
        when {
            requireContext().checkSelfPermission(android.Manifest.permission.CAMERA) ==
                    android.content.pm.PackageManager.PERMISSION_GRANTED -> {
                // Permission already granted
                startCamera()
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) -> {
                // Show rationale to the user (optional)
                Log.d("Permission", "Camera permission rationale needed")
                requestCameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            }
            else -> {
                // Directly request the permission
                requestCameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            }
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        } else {
            currentImageUri = null
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.ivCapturedImage.setImageURI(it)
        }
    }

    private fun submitData() {
        val energyKcal = binding.etTotalEnergy.text.toString().toFloatOrNull()
        val sugars = binding.etSugar.text.toString().toFloatOrNull()
        val saturatedFat = binding.etFat.text.toString().toFloatOrNull()
        val salt = binding.etSalt.text.toString().toFloatOrNull()
        val fruitsVegNuts = binding.etFruitVegNut.text.toString().toFloatOrNull()
        val fiber = binding.etFiber.text.toString().toFloatOrNull()
        val proteins = binding.etProtein.text.toString().toFloatOrNull()

        if (listOf(energyKcal, sugars, saturatedFat, salt, fruitsVegNuts, fiber, proteins).any { it == null }) {
            // Tampilkan pesan error jika ada input yang kosong
            Toast.makeText(requireContext(), "Semua field harus diisi dengan benar", Toast.LENGTH_SHORT).show()
            return
        }

        // Panggil API untuk mendapatkan predicted_grade
        val apiService = ApiConfig.getApiService()
        val request = NutriscoreRequest(
            energyKcal = energyKcal!!,
            sugars = sugars!!,
            saturatedFat = saturatedFat!!,
            salt = salt!!,
            fruitsVegNuts = fruitsVegNuts!!,
            fiber = fiber!!,
            proteins = proteins!!
        )
        apiService.predict(request).enqueue(object : Callback<NutriscoreResponse> {
            override fun onResponse(call: Call<NutriscoreResponse>, response: Response<NutriscoreResponse>) {
                if (response.isSuccessful) {
                    val predictedGrade = response.body()?.predictedGrade
                    binding.tvPredictedGrade.text = "Predicted Grade: $predictedGrade"
                    binding.tvPredictedGrade.visibility = View.VISIBLE
                } else {
                    Toast.makeText(requireContext(), "Gagal mendapatkan prediksi", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<NutriscoreResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

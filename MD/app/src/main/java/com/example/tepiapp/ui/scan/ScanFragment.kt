package com.example.tepiapp.ui.scan

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tepiapp.databinding.FragmentScanBinding
import getImageUri

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.example.tepiapp.ui.save

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tepiapp.data.adapter.SaveProductsAdapter
import com.example.tepiapp.data.response.ListDetailItem
import com.example.tepiapp.data.response.ListProductItem
import com.example.tepiapp.databinding.FragmentSaveBinding
import com.example.tepiapp.di.Injection
import com.example.tepiapp.ui.result.ResultActivity
import kotlinx.coroutines.launch

class SaveFragment : Fragment() {
    private var _binding: FragmentSaveBinding? = null
    private val binding get() = _binding!!
    private lateinit var productAdapter: SaveProductsAdapter
    private lateinit var saveViewModel: SaveViewModel
    private var productList: List<ListProductItem> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSaveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                saveViewModel.fetchProducts()
            }
        }

        setupRecyclerView(resultLauncher)

//        setupRecyclerView()
        setupViewModel()
        observeViewModel()
        setupSearchView()
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            val userRepository = Injection.provideRepository(requireContext())

            val factory = SaveViewModelFactory(userRepository)
            saveViewModel = ViewModelProvider(this@SaveFragment, factory).get(SaveViewModel::class.java)

            saveViewModel.getSession()
            saveViewModel.fetchProducts()
        }
    }

    private fun observeViewModel() {
        // Observe the product list from the ViewModel
        saveViewModel.productList.observe(viewLifecycleOwner) { products ->
            productList = products
            productAdapter.updateData(products)
        }

        saveViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        saveViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        // Observe productDetail only when user selects a product
//        saveViewModel.productDetail.observe(viewLifecycleOwner) { productDetail ->
//            if (productDetail != null) {
//                val intent = Intent(requireContext(), ResultActivity::class.java).apply {
//                    putExtra("productName", productDetail.productName)
//                    putExtra("energyKcal", productDetail.energyKcal100g)
//                    putExtra("sugars", productDetail.sugars100g)
//                    putExtra("saturatedFat", productDetail.saturatedFat100g)
//                    putExtra("salt", productDetail.salt100g)
//                    putExtra("fruitsVegNuts", productDetail.fruitsVegetablesNutsEstimateFromIngredients100g)
//                    putExtra("fiber", productDetail.fiber100g)
//                    putExtra("proteins", productDetail.proteins100g)
//                }
//                startActivity(intent)
//            } else {
//                Toast.makeText(requireContext(), "Product detail is unavailable", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    private fun setupRecyclerView(resultLauncher: ActivityResultLauncher<Intent>) {
        productAdapter = SaveProductsAdapter(listOf()) { product ->
            fetchProductDetail(product.id, resultLauncher)
        }

        binding.rvProduct.layoutManager = GridLayoutManager(context, 2)
        binding.rvProduct.adapter = productAdapter
    }

    private fun fetchProductDetail(productId: String, resultLauncher: ActivityResultLauncher<Intent>) {
        lifecycleScope.launch {
            try {
                // Fetch product details only when a product is selected
                saveViewModel.fetchProductDetail(productId)

                // Observe the product details once the fetch is complete
                saveViewModel.productDetail.observe(viewLifecycleOwner) { productDetail ->
                    navigateToDetail(productDetail, resultLauncher)  // Use the fetched product details
                }

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Failed to load product detail: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToDetail(detail: ListDetailItem, resultLauncher: ActivityResultLauncher<Intent>) {
        val intent = Intent(requireContext(), ResultActivity::class.java).apply {
            putExtra("product_name", detail.productName)
            putExtra("energy_kcal", detail.energyKcal100g)
            putExtra("sugars", detail.sugars100g)
            putExtra("saturated_fat", detail.saturatedFat100g)
            putExtra("salt", detail.salt100g)
            putExtra("fruits_veg_nuts", detail.fruitsVegetablesNutsEstimateFromIngredients100g)
            putExtra("fiber", detail.fiber100g)
            putExtra("proteins", detail.proteins100g)
            putExtra("predicted_grade", detail.nutriscoreGrade)
            putExtra("isBookmarked", true)

            // Flags to manage back stack behavior
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
//        startActivity(intent)
        resultLauncher.launch(intent)
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchProducts(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    productAdapter.updateData(productList)
                }
                return true
            }
        })

        binding.searchView.setOnCloseListener {
            productAdapter.updateData(productList)
            false
        }
    }

    private fun searchProducts(query: String) {
        val filteredList = productList.filter { it.name.contains(query, ignoreCase = true) }
        productAdapter.updateData(filteredList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

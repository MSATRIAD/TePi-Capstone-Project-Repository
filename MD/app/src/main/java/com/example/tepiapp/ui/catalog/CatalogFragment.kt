package com.example.tepiapp.ui.catalog

import CatalogViewModel
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tepiapp.Adapter
import com.example.tepiapp.data.UserRepository
import com.example.tepiapp.data.api.ApiConfig
import com.example.tepiapp.data.pref.UserPreference
import com.example.tepiapp.data.pref.dataStore
import com.example.tepiapp.data.response.ListProductItem
import com.example.tepiapp.databinding.FragmentCatalogBinding
import com.example.tepiapp.di.Injection
import com.example.tepiapp.ui.detail.DetailProductActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CatalogFragment : Fragment() {

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!
    private lateinit var productAdapter: Adapter
    private lateinit var catalogViewModel: CatalogViewModel
    private var productList: List<ListProductItem> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        setupViewModel()

        observeViewModel()

        setupSearchView()
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            val userRepository = Injection.provideRepository(requireContext())

            val factory = CatalogViewModelFactory(userRepository)
            catalogViewModel = ViewModelProvider(this@CatalogFragment, factory).get(CatalogViewModel::class.java)

            catalogViewModel.fetchProducts()

            catalogViewModel.getSession()
        }
    }

    private fun observeViewModel() {
        catalogViewModel.productList.observe(viewLifecycleOwner) { products ->
            productList = products
            productAdapter.updateData(products)
        }

        catalogViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        catalogViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        catalogViewModel.userSession.observe(viewLifecycleOwner) { session ->
            Toast.makeText(requireContext(), "Logged in as: ${session.email}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        productAdapter = Adapter(listOf()) { product ->
            val intent = Intent(requireContext(), DetailProductActivity::class.java)
            intent.putExtra("productId", product.id)
            startActivity(intent)
        }

        binding.rvProduct.layoutManager = GridLayoutManager(context, 2)
        binding.rvProduct.adapter = productAdapter
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

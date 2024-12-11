package com.example.tepiapp.ui.catalog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tepiapp.Adapter
import com.example.tepiapp.data.response.ListProductItem
import com.example.tepiapp.databinding.FragmentCatalogBinding
import com.example.tepiapp.ui.detail.DetailProductActivity

class CatalogFragment : Fragment() {

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!
    private lateinit var productAdapter: Adapter
    private lateinit var catalogViewModel: CatalogViewModel
    private var productList: List<ListProductItem> = listOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        catalogViewModel = ViewModelProvider(this)[CatalogViewModel::class.java]

        setupRecyclerView()

        catalogViewModel.productList.observe(viewLifecycleOwner) { events ->
            productList = events
            productAdapter.updateData(events)
        }

        catalogViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        catalogViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
        setupSearchView()
    }

    private fun setupRecyclerView() {
//        productAdapter = Adapter(listOf())
        productAdapter = Adapter(listOf()) { product ->
            val intent = Intent(requireContext(), DetailProductActivity::class.java)
            intent.putExtra("productId", product.id)
            startActivity(intent)
        }

        binding.rvProduct.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = productAdapter
        }
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
        val filteredList = productList.filter { event ->
            event.name.contains(query, ignoreCase = true)
        }
        productAdapter.updateData(filteredList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
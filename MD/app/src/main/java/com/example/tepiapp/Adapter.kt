package com.example.tepiapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tepiapp.data.response.ListProductItem

class Adapter(private var productList: List<ListProductItem>, private val onItemClick: (ListProductItem) -> Unit) : RecyclerView.Adapter<Adapter.ProductViewHolder>() {

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.tvProductName)
        val productGrade: TextView = view.findViewById(R.id.tvProductGrade)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val event = productList[position]

        holder.productName.text = event.name
        holder.productGrade.text = event.grade

        holder.itemView.setOnClickListener {
            onItemClick(event) // Panggil callback dengan data produk yang diklik
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun updateData(newProductList: List<ListProductItem>) {
        productList = newProductList
        notifyDataSetChanged()
    }
}
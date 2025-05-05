package com.example.mitienda.recicler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mitienda.R
import com.example.mitienda.model.Product

class ProductAdapter(private val onProductClick: (Product) -> Unit) : RecyclerView.Adapter<ProductView>() {

    private var productos: List<Product> = emptyList()

    fun setProductos(nuevosProductos: List<Product>) {
        productos = nuevosProductos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductView {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductView(view, onProductClick)
    }

    override fun onBindViewHolder(holder: ProductView, position: Int) {
        val producto = productos[position]
        holder.bind(producto)
    }

    override fun getItemCount(): Int = productos.size
}
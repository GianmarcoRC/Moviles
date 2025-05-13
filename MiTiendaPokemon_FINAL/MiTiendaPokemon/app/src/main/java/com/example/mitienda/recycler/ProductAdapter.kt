package com.example.mitienda.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mitienda.R
import com.example.mitienda.models.Category
import com.example.mitienda.models.Product

class ProductAdapter(val onProductClick: (Product) -> Unit, private val categories: List<Category>) : RecyclerView.Adapter<ProductView>() {

    private var productos: List<Product> = emptyList()

    fun setProductos(productos: List<Product>) {
        this.productos = productos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductView {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductView(view, onProductClick)
    }

    override fun onBindViewHolder(holder: ProductView, position: Int) {
        val producto = productos[position]
        val categoryName = categories.find { it.id == producto.categoryId }?.name ?: ""
        holder.bind(producto, categoryName)
    }


    override fun getItemCount(): Int = productos.size
}
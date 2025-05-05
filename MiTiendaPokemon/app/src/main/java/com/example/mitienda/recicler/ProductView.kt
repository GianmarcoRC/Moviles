package com.example.mitienda.recicler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mitienda.R
import com.example.mitienda.model.Product

class ProductView(itemView: View, private val onProductClick: (Product) -> Unit) : RecyclerView.ViewHolder(itemView) {

    // Obtener referencias a las vistas del item_product.xml
    val productNameTextView: TextView = itemView.findViewById(R.id.textViewProductName)
    val productPriceTextView: TextView = itemView.findViewById(R.id.textViewPrice)
    val imagenProducto: ImageView = itemView.findViewById(R.id.imageViewProduct)

    fun bind(product: Product) {
        productNameTextView.text = product.name
        productPriceTextView.text = "$${product.price}"

        Glide.with(itemView.context)
            .load(product.image)
            .into(imagenProducto)

        itemView.setOnClickListener {
            onProductClick(product)
        }
    }
}

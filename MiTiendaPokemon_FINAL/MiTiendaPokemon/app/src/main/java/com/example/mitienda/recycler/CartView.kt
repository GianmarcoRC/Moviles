package com.example.mitienda.recycler

import androidx.recyclerview.widget.RecyclerView
import com.example.mitienda.databinding.ItemCarroBinding
import com.example.mitienda.models.ShoppingCartDto

class CartView(val binding: ItemCarroBinding, private val onItemClick: (ShoppingCartDto) -> Unit) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var currentItem: ShoppingCartDto

    init {
        binding.root.setOnClickListener {
            onItemClick(currentItem)
        }
    }

    fun bind(item: ShoppingCartDto) {
        currentItem = item
        binding.textProductName.text = item.name
        binding.textQuantity.text = "Cantidad: ${item.quantity}"
        binding.textUnitPrice.text = "${String.format("%.2f", item.totalPrice ?: 0.0)}€"
    }
}
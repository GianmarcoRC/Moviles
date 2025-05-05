package com.example.mitienda.model

data class Product( val id: Int,
                    val name: String,
                    val price: Double,
                    val quantity: Int,
                    val image: String,
                    val categoryId: Long)

data class Category( val id: Long,
                     val name: String)
data class Page<T>(
    val content: List<T>,
    val totalPages: Int,
    val totalElements: Long,
    val number: Int,
    val size: Int,
    val hasNext: Boolean,
    val hasPrevious: Boolean
)
data class CartDto(
    val id: Long,
    val totalAmount: Double,
    val items: List<CartItemDto>
)

data class CartItemDto(
    val id: Long,
    val productId: Long,
    val name: String,
    val price: Double,
    val quantity: Int,
    val totalPrice: Double
)

package com.example.mitienda.models


data class Product(
    val id: Long,
    val name: String,
    val price: Double,
    val image: String,
    val categoryId: Long,
    val description: String? = null
)
data class Category(
    val id: Long,
    val name: String
)
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
    val shoppingCartProducts: List<ShoppingCartDto>?,
    val totalCartQuantity: Int?,
    val totalAmount: Double?
)

data class ShoppingCartDto(
    val id: Long?,
    val name: String?,
    val quantity: Int?,
    val price: Double?,
    val totalPrice: Double?,
    val userId: Long?
)

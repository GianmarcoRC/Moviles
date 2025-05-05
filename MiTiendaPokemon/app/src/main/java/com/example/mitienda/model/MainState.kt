package com.example.mitienda.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class MainState {
    private val baseUrl = "http://10.0.2.2:8000/api/app/v1/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build())
        .build()

    private val productApiService = retrofit.create(ApiService::class.java)
    private val categoryApiService = retrofit.create(ApiService::class.java)
    private val cartApiService = retrofit.create(ApiService::class.java)

    suspend fun getProductsPaginated(
        search: String? = null,
        categoryId: Long? = null,
        pageNumber: Int = 1,
        pageSize: Int = 5,
        sortBy: String = "name",
        sortDir: String = "asc"
    ): Page<Product> {
        val response = productApiService.getProducts(
            search, categoryId, sortBy, sortDir, pageNumber, pageSize
        )

        if (response.isSuccessful) {
            return response.body() ?: throw Exception("No se encontraron productos")
        } else {
            throw Exception("Error al cargar los productos: ${response.code()}")
        }
    }

    suspend fun getCategories(): List<Category> {
        val response = categoryApiService.getCategories()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Error al cargar categorías: ${response.code()}")
        }
    }

    suspend fun addToCart(productId: Long, quantity: Int): CartDto {
        val response = cartApiService.addToCart(productId, quantity)
        if (response.isSuccessful) {
            return response.body()?.firstOrNull() ?: throw Exception("Error al añadir al carrito")
        } else {
            throw Exception("Error al añadir al carrito: ${response.code()}")
        }
    }
}
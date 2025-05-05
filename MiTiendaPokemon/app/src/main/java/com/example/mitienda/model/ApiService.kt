package com.example.mitienda.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("/api/app/v1/cart/{productId}/{count}")
    suspend fun addToCart(
        @Path("productId") productId: Long,
        @Path("count") count: Int
    ): Response<List<CartDto>>

    @GET("/api/app/v1/cart")
    suspend fun getCart(): Response<List<CartDto>>

    @GET("/api/app/v1/categories")
    suspend fun getCategories(): Response<List<Category>>
    @GET("/api/app/v1/products/find")
    suspend fun getProducts(
        @Query("search") search: String? = null,
        @Query("cat") categoryId: Long? = null,
        @Query("sortBy") sortBy: String = "name",
        @Query("sortDir") sortDir: String = "asc",
        @Query("pageNumber") pageNumber: Int = 1,
        @Query("pageSize") pageSize: Int = 5
    ): Response<Page<Product>>
}
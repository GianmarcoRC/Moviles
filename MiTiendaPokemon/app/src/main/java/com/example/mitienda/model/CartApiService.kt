package com.example.mitienda.model

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CartApiService {
    @POST("/api/app/v1/cart/{productId}/{count}")
    suspend fun addToCart(
        @Query("productId") idProducto: Long,
        @Query("count") cantidad: Int
    ): Response<List<CartDto>>

    @GET("/api/app/v1/cart")
    suspend fun getCart(): Response<List<CartDto>>

    @DELETE("/api/app/v1/cart/{shoppingCartProductId}")
    suspend fun removeFromCart(
        @Path("shoppingCartProductId") idItem: Long
    ): Response<List<CartDto>>
}
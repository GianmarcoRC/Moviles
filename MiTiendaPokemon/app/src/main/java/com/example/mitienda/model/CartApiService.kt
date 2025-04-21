package com.example.mitienda.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CartApiService {
    @POST("/api/app/v1/cart/{productId}/{count}")
    suspend fun addToCart(
        @Path("productId") productId: Long,
        @Path("count") count: Int
    ): Response<List<CartDto>>

    @GET("/api/app/v1/cart")
    suspend fun getCart(): Response<List<CartDto>>
}
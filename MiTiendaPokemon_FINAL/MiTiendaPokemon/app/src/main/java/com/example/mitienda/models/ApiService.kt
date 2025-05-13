package com.example.mitienda.models

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Locale // Puedes eliminar esta importación si ya no la usas

interface ApiService {
    @POST("/api/v1/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<TokensResponse>

    @POST("/api/app/v1/cart/{productId}/{count}")
    suspend fun addToCart(
        @Path("productId") productId: Long,
        @Path("count") count: Int,
        @Header("Authorization") authToken: String
    ): Response<CartDto?>

    @GET("/api/app/v1/cart")
    suspend fun getCart(@Header("Authorization") authToken: String): Response<CartDto>

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

    @DELETE("/api/app/v1/cart/{shoppingCartProductId}")
    suspend fun eliminarDelCarrito(
        @Path("shoppingCartProductId") shoppingCartProductId: Long,
        @Header("Authorization") authToken: String
    ): Response<CartDto>

    data class LoginRequest(val email: String, val password: String)
    data class TokensResponse(val accessToken: String, val refreshToken: String)
}
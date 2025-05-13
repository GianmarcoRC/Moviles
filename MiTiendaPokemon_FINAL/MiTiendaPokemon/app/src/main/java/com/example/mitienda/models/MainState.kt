package com.example.mitienda.models

import com.example.mitienda.utils.Constants
import retrofit2.Retrofit
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import java.util.concurrent.TimeUnit

class MainState(private val tokenProvider: TokenProvider) {

    private val baseUrl = Constants.BASE_URL
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val token = tokenProvider.getToken()
            val newRequest = originalRequest.newBuilder()
                .apply {
                    if (!token.isNullOrBlank()) {
                        addHeader("Authorization", "Bearer $token")
                    }
                }
                .build()
            chain.proceed(newRequest)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    private val authApiService = retrofit.create(ApiService::class.java)
    private val productApiService = retrofit.create(ApiService::class.java)
    private val categoryApiService = retrofit.create(ApiService::class.java)
    private val cartApiService = retrofit.create(ApiService::class.java)

    suspend fun login(email: String, password: String): ApiService.TokensResponse? {
        val response = authApiService.login(ApiService.LoginRequest(email, password))
        return if (response.isSuccessful) response.body() else null
    }

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
            return response.body() ?: throw Exception("Error al cargar categorías")
        } else {
            throw Exception("Error al cargar categorías: ${response.code()}")
        }
    }

    suspend fun addToCart(productId: Long, quantity: Int): Response<CartDto?> {
        val token = tokenProvider.getToken()
        if (token.isNullOrBlank()) {
            val errorBody = "Token no disponible".toResponseBody("text/plain".toMediaTypeOrNull())
            return Response.error(401, errorBody!!)
        }
        return try {
            cartApiService.addToCart(productId, quantity, "Bearer $token")
        } catch (e: Exception) {
            val errorBody = okhttp3.ResponseBody.create(null, e.message ?: "Error al añadir al carrito")
            Response.error(500, errorBody)
        }
    }

    suspend fun getCart(): CartDto? {
        val token = tokenProvider.getToken()
        if (token.isNullOrBlank()) throw Exception("Token no disponible")
        val response = cartApiService.getCart("Bearer $token")
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun deleteFromCart(itemId: Long?): CartDto? {
        val token = tokenProvider.getToken()
        if (token.isNullOrBlank()) throw Exception("Token no disponible")
        itemId?.let { id ->
            val response = cartApiService.eliminarDelCarrito(id, "Bearer $token")
            if (response.isSuccessful) {
                return response.body()
            } else {
                throw Exception("Error al eliminar el producto del carrito: ${response.code()}")
            }
        }
        return null
    }

    interface TokenProvider {
        fun getToken(): String?
    }
}
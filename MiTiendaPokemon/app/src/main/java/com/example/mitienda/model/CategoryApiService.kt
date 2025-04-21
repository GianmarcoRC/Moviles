package com.example.mitienda.model

import retrofit2.Response
import retrofit2.http.GET

interface CategoryApiService {
    @GET("/api/app/v1/categories")
    suspend fun getCategories(): Response<List<Category>>
}
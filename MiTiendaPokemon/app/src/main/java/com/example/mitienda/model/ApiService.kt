package com.example.mitienda.model

import retrofit2.http.POST

interface ApiService {


    suspend fun getProducts(): List<Product>


    suspend fun getCategories(): List<Category> {

        return emptyList()
    }
}
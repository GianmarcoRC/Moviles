package com.example.mitienda.model

import android.graphics.pdf.PdfRendererPreV
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductApiService {
    @GET("/api/app/v1/products/find")
    suspend fun getProducts(
        @Query("search") search: String? = null,
        @Query("cat") categoryId: Long? = null,
        @Query("sortBy") sortBy: String = "name",
        @Query("sortDir") sortDir: String = "asc",
        @Query("pageNumber") pageNumber: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): Response<Page<Product>>
}
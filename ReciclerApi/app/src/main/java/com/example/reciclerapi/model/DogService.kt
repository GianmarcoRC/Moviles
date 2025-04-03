package com.example.reciclerapi.model

import retrofit2.Response
import retrofit2.http.GET

interface DogService {
    @GET("images")
    suspend fun getDogImages(): Response<DogRespuesta>
}
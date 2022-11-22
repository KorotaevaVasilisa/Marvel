package com.example.marvel.api

import com.example.marvel.api.model.Information
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelApiService {
    @GET("v1/public/characters")
    suspend fun getCharacters(): NetworkResult<Information>

    @GET("v1/public/characters/{characterId}")
    suspend fun getCharacter(
        @Path("characterId") id: Int
    ): NetworkResult<Information>
}

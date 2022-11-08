package com.example.marvel.api

import com.example.marvel.api.model.Information
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


const val BASE_URL = "https://gateway.marvel.com/"
const val TIME = "1"
const val PUBLIC_KEY = "0cff46d6073f696863bdbcf7ea1ee61c"
const val HASH = "a963d2d10fbb7bd7f93ada240b258839"


interface MarvelApiService {
    @GET("v1/public/characters")
    suspend fun getCharacters(
        @Query("ts") ts: String = TIME,
        @Query("apikey") apikey: String = PUBLIC_KEY,
        @Query("hash") hash: String = HASH
    ): Information

    @GET("v1/public/characters/{characterId}")
    suspend fun getCharacter(
        @Path("characterId") id: Int,
        @Query("ts") ts: String = TIME,
        @Query("apikey") apikey: String = PUBLIC_KEY,
        @Query("hash") hash: String = HASH
    ): Information
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

object MarvelApi {
    val retrofitService: MarvelApiService by lazy { retrofit.create(MarvelApiService::class.java) }
}

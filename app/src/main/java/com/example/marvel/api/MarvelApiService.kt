package com.example.marvel.api

import com.example.marvel.api.model.Information
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelApiService {
    @GET("v1/public/characters")
    suspend fun getCharacters(): Information

    @GET("v1/public/characters/{characterId}")
    suspend fun getCharacter(
        @Path("characterId") id: Int
    ): Information
}

//private val moshi = Moshi.Builder()
//    .add(KotlinJsonAdapterFactory())
//    .build()
//
//private val okhttp=OkHttpClient.Builder().addInterceptor(MarvelApiInterceptor()).build()
//
//private val retrofit = Retrofit.Builder()
//    .addConverterFactory(MoshiConverterFactory.create(moshi))
//    .client(okhttp)
//    .baseUrl(Constants.BASE_URL)
//    .build()
//
//object MarvelApi {
//    val retrofitService: MarvelApiService by lazy { retrofit.create(MarvelApiService::class.java) }
//}

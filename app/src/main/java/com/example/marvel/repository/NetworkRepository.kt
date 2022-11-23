package com.example.marvel.repository

import com.example.marvel.api.MarvelApiService
import com.example.marvel.api.NetworkResult
import com.example.marvel.api.model.Information
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val marvelApiService: MarvelApiService
) {
    suspend fun getAllHeroes(): NetworkResult<Information> {
        return marvelApiService.getCharacters()
    }

    suspend fun getHero(id: Int): NetworkResult<Information> {
        return marvelApiService.getCharacter(id)
    }
}

package com.example.marvel.repository

import com.example.marvel.api.MarvelApiService
import com.example.marvel.data.Hero
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val marvelApiService: MarvelApiService
) {
    suspend fun getAllHeroes(): List<Hero> = withContext(Dispatchers.IO) {
        marvelApiService.getCharacters().data.heroes.map { it.toHero() }
    }

    suspend fun getHero(id: Int): Hero = withContext(Dispatchers.IO) {
        marvelApiService.getCharacter(id).data.heroes.first().toHero()
    }
}

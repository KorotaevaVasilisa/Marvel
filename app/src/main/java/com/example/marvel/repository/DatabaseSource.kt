package com.example.marvel.repository

import com.example.marvel.data.Hero
import com.example.marvel.data.dao.HeroesDao
import javax.inject.Inject

class DatabaseSource @Inject constructor(private val dao: HeroesDao) {
    suspend fun getHeroes(): List<Hero> {
        return dao.getAll()
    }

    suspend fun insertHeroes(list: List<Hero>) {
        dao.insertHeroes(list)
    }

    suspend fun getHeroCached(id: Int): Hero = dao.getHero(id)
}

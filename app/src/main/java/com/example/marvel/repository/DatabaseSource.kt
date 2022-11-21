package com.example.marvel.repository

import com.example.marvel.data.Hero
import com.example.marvel.data.dao.HeroesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DatabaseSource @Inject constructor(private val dao: HeroesDao) {
    suspend fun getHeroes(): Flow<List<Hero>> {
        val res = flow {
            val response = dao.getAll()
            emit(response)
        }
        return res
    }

    suspend fun insertHeroes(list: List<Hero>) {
        dao.insertHeroes(list)
    }

    suspend fun getHeroCached(id: Int): Flow<Hero> {
        val res = flow {
            val response = dao.getHero(id)
            emit(response)
        }
        return res
    }
}

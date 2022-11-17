package com.example.marvel.data

import com.example.marvel.api.model.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HeroesRepository(private val heroesDao: HeroesDao) {
    val allHeroes: Flow<List<HeroEntity>>
        get() = heroesDao.getAll()

    suspend fun insert(hero: HeroEntity) {
        heroesDao.addHero(hero)
    }

    private suspend fun findHeroById(id: Int): Flow<Hero> {
        return heroesDao.getHero(id).map { value: HeroEntity -> value?.toHero() }
    }

}

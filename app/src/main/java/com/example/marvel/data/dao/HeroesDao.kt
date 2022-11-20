package com.example.marvel.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marvel.data.Hero
import kotlinx.coroutines.flow.Flow

@Dao
interface HeroesDao {

    @Query("SELECT * FROM heroes WHERE id = :id")
    fun getHero(id: Int): Flow<Hero>

    @Query("SELECT * FROM heroes")
    fun getAll(): Flow<List<Hero>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeroes(hero: Hero)
}


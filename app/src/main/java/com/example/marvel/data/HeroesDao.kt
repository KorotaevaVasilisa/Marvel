package com.example.marvel.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HeroesDao {

    @Query("SELECT * FROM heroes WHERE id = :id")
    fun getHero(id: Int): Flow<HeroEntity>

    @Query("SELECT * FROM heroes")
    fun getAll(): Flow<List<HeroEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHero(hero: HeroEntity)
}


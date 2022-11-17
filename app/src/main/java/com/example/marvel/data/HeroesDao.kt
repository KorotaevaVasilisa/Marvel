package com.example.marvel.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marvel.api.model.Hero

@Dao
interface HeroesDao {
    @Query("SELECT * FROM heroes")
    suspend fun getAll(): List<Hero>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(restaurants: List<Hero>)
}


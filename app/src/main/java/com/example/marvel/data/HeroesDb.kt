package com.example.marvel.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.marvel.data.dao.HeroesDao

@Database(
    entities = [Hero::class],
    version = 1,
    exportSchema = false
)
abstract class HeroesDb : RoomDatabase() {
    abstract fun getHeroesDao(): HeroesDao

}

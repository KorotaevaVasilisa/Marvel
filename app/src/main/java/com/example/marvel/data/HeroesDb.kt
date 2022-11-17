package com.example.marvel.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [HeroEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HeroesDb : RoomDatabase() {
    abstract fun getHeroesDao(): HeroesDao

    companion object {
        @Volatile
        private var INSTANCE: HeroesDb? = null

        fun getDatabase(context: Context): HeroesDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HeroesDb::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance as HeroesDb
                instance as HeroesDb
            }
        }
    }
}

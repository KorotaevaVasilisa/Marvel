package com.example.marvel.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.marvel.api.model.Hero

@Database(
    entities = [Hero::class],
    version = 1,
    exportSchema = false
)
abstract class HeroesDb : RoomDatabase() {
    abstract val dao: HeroesDao

    companion object {

        @Volatile
        private var INSTANCE: HeroesDao? = null
        fun getDaoInstance(context: Context): HeroesDao {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = buildDatabase(context).dao
                    INSTANCE = instance
                }
                return instance
            }
        }

        private fun buildDatabase(context: Context):
                HeroesDb =
            Room.databaseBuilder(
                context.applicationContext,
                HeroesDb::class.java,
                "heroes_database"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}
package com.example.marvel.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.marvel.api.model.Hero
import com.example.marvel.api.model.Thumbnail

@Entity(tableName = "heroes")
data class HeroEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "name_hero")
    val name: String,
    @ColumnInfo(name = "info")
    val description: String,
    val path: String,
) {
    fun toHero(): Hero = Hero(
        id = id,
        name = name,
        description = description,
        thumbnail = Thumbnail(".jpg", path)
    )
}

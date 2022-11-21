package com.example.marvel.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "heroes")
data class Hero(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "name_hero")
    val name: String,
    @ColumnInfo(name = "info")
    val description: String,
    @ColumnInfo(name = "path")
    val path: String,
)

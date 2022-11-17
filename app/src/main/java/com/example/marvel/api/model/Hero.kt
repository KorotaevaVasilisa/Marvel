package com.example.marvel.api.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "heroes")
@JsonClass(generateAdapter = true)
data class Hero(
    @PrimaryKey
    @Json(name = "id")
    val id: Int,
    @ColumnInfo(name = "name_hero")
    @Json(name = "name")
    val name: String,
    @ColumnInfo(name = "info")
    @Json(name = "description")
    val description: String,
    @Json(name = "thumbnail")
    val thumbnail: Thumbnail,
)

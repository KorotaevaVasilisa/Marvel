package com.example.marvel.data

sealed class HeroState {
    object Loading : HeroState()
    data class Data<T>(val data: T) : HeroState()
    data class Error<T>(val data: T, val message: String):HeroState()
}



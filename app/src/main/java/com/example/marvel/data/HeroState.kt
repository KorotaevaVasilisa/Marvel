package com.example.marvel.data

data class HeroState<T>(
    val data: T,
    val isLoading: Boolean,
    val error: String? = null
)

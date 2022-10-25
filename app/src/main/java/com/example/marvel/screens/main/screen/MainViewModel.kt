package com.example.marvel.screens.main.screen


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _heroes = MutableStateFlow<List<Hero>>(emptyList())
    val heroes: StateFlow<List<Hero>> = _heroes.asStateFlow()

    init {
        getAllHeroes()
    }

    private lateinit var currentHero: Hero

    fun getCurrentHero(index: Int = 0): Hero {
        currentHero = heroes.value[index]
        return currentHero
    }

    private fun getAllHeroes() {
        _heroes.value = info
    }

    fun lerp(start: Float, stop: Float, fraction: Float): Float {
        return (1 - fraction) * start + fraction * stop
    }


}


val info = listOf<Hero>(
    Hero(
        id = 0,
        name = "Thor",
        image = "https://i.annihil.us/u/prod/marvel/i/mg/d/d0/5269657a74350.jpg",
        information = ""
    ),
    Hero(
        id = 1,
        name = "Captain",
        image = "https://i.annihil.us/u/prod/marvel/i/mg/3/50/537ba56d31087.jpg",
        information = ""
    ),
    Hero(
        id = 2,
        name = "Iron man",
        image = "https://i.annihil.us/u/prod/marvel/i/mg/9/c0/527bb7b37ff55.jpg",
        information = ""
    ),
    Hero(
        id = 3,
        name = "Deadpool",
        image = "https://i.annihil.us/u/prod/marvel/i/mg/9/90/5261a86cacb99.jpg",
        information = ""
    ),
)


data class Hero(
    val id: Int = 0,
    val name: String = "",
    val image: String = "",
    val information: String = ""
)
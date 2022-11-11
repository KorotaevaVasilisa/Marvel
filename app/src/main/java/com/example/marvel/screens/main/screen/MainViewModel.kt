package com.example.marvel.screens.main.screen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel.api.MarvelApi
import com.example.marvel.api.model.Hero
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _heroes = MutableStateFlow<List<Hero>>(emptyList())
    val heroes: StateFlow<List<Hero>> = _heroes.asStateFlow()
    var hasError = MutableStateFlow<Boolean>(false)

    init {
        getAllHeroes()
    }

    private fun getAllHeroes() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            hasError.value = true
        }
        viewModelScope.launch(exceptionHandler) {
                _heroes.value = MarvelApi.retrofitService.getCharacters().data.heroes
        }
    }
}

package com.example.marvel.screens.main.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel.api.onError
import com.example.marvel.api.onException
import com.example.marvel.api.onSuccess
import com.example.marvel.data.HeroState
import com.example.marvel.repository.DatabaseSource
import com.example.marvel.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val dataRepository: DatabaseSource
) : ViewModel() {
    private val _heroes =
        MutableStateFlow<HeroState>(HeroState.Loading)
    val heroes: StateFlow<HeroState> = _heroes.asStateFlow()

    init {
        getAllHeroes()
    }

    private fun getAllHeroes() {
        viewModelScope.launch() {
            val response = networkRepository.getAllHeroes()
            response.onSuccess { heroesList ->
                val result = heroesList.data.heroes.map { it.toHero() }
                dataRepository.insertHeroes(result)
                _heroes.update { HeroState.Data(result) }
            }
                .onError { code, message ->
                    val deferred = async { dataRepository.getHeroes() }
                    val result = deferred.await()
                    _heroes.update { HeroState.Error(result, "Ошибка $message $code") }
                }
                .onException { error ->
                    val deferred = async { dataRepository.getHeroes() }
                    val result = deferred.await()
                    _heroes.update { HeroState.Error(result, "Ошибка $error ") }

                }
        }
    }
}


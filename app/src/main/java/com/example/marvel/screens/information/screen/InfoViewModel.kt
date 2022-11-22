package com.example.marvel.screens.information.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel.api.onError
import com.example.marvel.api.onException
import com.example.marvel.api.onSuccess
import com.example.marvel.data.Hero
import com.example.marvel.data.HeroState
import com.example.marvel.repository.DatabaseSource
import com.example.marvel.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val networkRepository: NetworkRepository,
    private val dataRepository: DatabaseSource
) : ViewModel() {

    private val _hero = MutableStateFlow<HeroState<Hero>>(HeroState(Hero(0, "", "", ""), false))
    val hero: StateFlow<HeroState<Hero>> = _hero.asStateFlow()

    init {
        val id = stateHandle.get<Int>("heroId") ?: 0
        getHero(id)
    }


    private fun getHero(id: Int) {
        viewModelScope.launch() {
            val response = networkRepository.getHero(id)
            response.onSuccess {
                val result = it.data.heroes.first().toHero()
                _hero.update { HeroState(result, true) }
            }
                .onException { e ->
                    _hero.update { HeroState(dataRepository.getHeroCached(id), true, e.message) }
                }
                .onError { code, message ->
                    _hero.update {
                        HeroState(dataRepository.getHeroCached(id), true, "Error $code : $message")
                    }
                }
        }
    }
}




package com.example.marvel.screens.information.screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import retrofit2.HttpException
import java.net.ConnectException
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
            try {
                val result = networkRepository.getHero(id)
                _hero.update { HeroState(result, true) }
            } catch (throwable: Throwable) {
                _hero.update {
                    HeroState(
                        dataRepository.getHeroCached(id),
                        true,
                        throwable.message
                    )
                }
                when (throwable) {
                    is ConnectException -> {
                        Log.e("Network", "ERROR : " + throwable.localizedMessage)
                    }
                    is HttpException -> {
                        val code = throwable.code()
                        Log.e("RETROFIT", "ERROR :$code " + throwable.localizedMessage)
                    }
                    else -> {
                        Log.e("Error", throwable.localizedMessage)
                    }
                }
            }
        }
    }
}




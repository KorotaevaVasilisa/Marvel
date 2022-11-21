package com.example.marvel.screens.main.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel.data.Hero
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
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val dataRepository: DatabaseSource
) :
    ViewModel() {
    private val _heroes =
        MutableStateFlow<HeroState<List<Hero>>>(HeroState<List<Hero>>(emptyList(), true))
    val heroes: StateFlow<HeroState<List<Hero>>> = _heroes.asStateFlow()

    init {
        getAllHeroes()
    }

    private fun getAllHeroes() {
        viewModelScope.launch() {
            try {
                val result = networkRepository.getAllHeroes()
                dataRepository.insertHeroes(result)
                _heroes.update { HeroState(result, false) }
            } catch (throwable: Throwable) {
                val deferred = async { dataRepository.getHeroes() }
                val result = deferred.await()
                _heroes.update { HeroState(result, false, throwable.message) }
                when (throwable) {
                    is UnknownHostException -> {
                        Log.e("Network", "ERROR : " + throwable.localizedMessage)
                    }
                    is HttpException -> {
                        val code = throwable.code()
                        Log.e("RETROFIT", "ERROR :$code " + throwable.localizedMessage)
                    }
                    else -> {
                        Log.e("Error", "" + throwable.localizedMessage)
                    }
                }
            }
        }
    }
}

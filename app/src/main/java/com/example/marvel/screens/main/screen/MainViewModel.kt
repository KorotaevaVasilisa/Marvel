package com.example.marvel.screens.main.screen


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel.api.MarvelApi
import com.example.marvel.api.model.Hero
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.ConnectException
import java.net.SocketException

class MainViewModel : ViewModel() {
    private val _heroes = MutableStateFlow<List<Hero>>(emptyList())
    val heroes: StateFlow<List<Hero>> = _heroes.asStateFlow()

    init {
        getAllHeroes()
    }

    private fun getAllHeroes() {
        viewModelScope.launch() {
            try{
                _heroes.value = MarvelApi.retrofitService.getCharacters().data.heroes
            } catch (e: ConnectException) {
                Log.e("RETROFIT", "ERROR : " + e.localizedMessage)
            } catch (e: SocketException) {
                Log.e("RETROFIT", "ERROR : " + e.localizedMessage)
            } catch (e: IOException) {
                Log.e("RETROFIT", "ERROR : " + e.localizedMessage)
            }
        }
    }
}

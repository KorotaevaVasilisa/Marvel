package com.example.marvel.screens.information.screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel.api.MarvelApi
import com.example.marvel.api.model.Hero
import com.example.marvel.api.model.Thumbnail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.ConnectException
import java.net.SocketException

class InfoViewModel(private val stateHandle: SavedStateHandle) : ViewModel() {

    private val _hero = MutableStateFlow<Hero?>(Hero(0, "", "", Thumbnail("", "")))
    val hero: StateFlow<Hero?> = _hero.asStateFlow()

    init {
        val id = stateHandle.get<Int>("heroId") ?: 0
        getHero(id)
    }

    private fun getHero(id: Int) {
        viewModelScope.launch {
            try {
                _hero.value = MarvelApi.retrofitService.getCharacter(id = id).data.heroes.first()
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



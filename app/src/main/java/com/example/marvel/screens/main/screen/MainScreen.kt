package com.example.marvel.screens.main.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.marvel.data.Hero
import com.example.marvel.data.HeroState
import com.example.marvel.screens.components.ShowAlert
import com.github.satoshun.compose.palette.coil.rememberCoilPaletteState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import kotlin.math.absoluteValue

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val heroesState by mainViewModel.heroes.collectAsState()
    val color = remember {
        mutableStateOf(Color.Gray)
    }

    Main(
        heroesState = heroesState,
        currentColor = color,
        navController = navController,
    )
}


@Composable
fun Main(
    heroesState: HeroState,
    currentColor: MutableState<Color>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Logo(
            modifier = Modifier
                .height(85.dp)
                .padding(20.dp)
        )

        when (heroesState) {
            is HeroState.Loading -> {
                Box {
                    CircularProgressIndicator(
                        modifier = modifier
                            .size(20.dp)
                            .align(Alignment.Center)
                    )
                }
            }
            is HeroState.Error<*> -> {
                ShowAlert(message = heroesState.message)
                RowHeroes(
                    currentColor = currentColor,
                    heroes = heroesState.data as List<Hero>,
                    navController = navController
                )
            }
            is HeroState.Data<*> -> {
                RowHeroes(
                    currentColor = currentColor,
                    heroes = heroesState.data as List<Hero>,
                    navController = navController
                )
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RowHeroes(
    currentColor: MutableState<Color>,
    heroes: List<Hero>,
    navController: NavController
) {
    HorizontalPager(
        count = heroes.size,
        contentPadding = PaddingValues(32.dp),
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                rotate(degrees = 45f) {
                    drawRect(
                        color = currentColor.value,
                        topLeft = Offset(x = 450f, y = 150f),
                        size = size / 1f
                    )
                }
            }
    ) { page ->

        val paletteState = rememberCoilPaletteState(
            data = heroes[currentPage].path,
            builder = {
                crossfade(true)
                allowHardware(false)
            })
        if (paletteState.vibrant != null)
            currentColor.value = paletteState.vibrant!!

        CardOfHero(
            currentOffset = calculateCurrentOffsetForPage(page).absoluteValue,
            navController = navController,
            hero = heroes[page]
        )
    }
}



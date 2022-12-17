package com.example.marvel.screens.main.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.marvel.data.Hero
import com.example.marvel.data.HeroState
import com.example.marvel.screens.components.ShowAlert
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    val heroesState by mainViewModel.heroes.collectAsState()
    val color = remember {
        mutableStateOf(Color.Gray)
    }
    val state = rememberPagerState()
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            MainHorizontal(
                heroesState = heroesState,
                currentColor = color,
                navController = navController,
                pagerState =state
            )
        }
        else -> {
            MainVertical(
                heroesState = heroesState,
                currentColor = color,
                navController = navController,
                pagerState = state
            )
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainVertical(
    heroesState: HeroState,
    currentColor: MutableState<Color>,
    navController: NavController,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Logo()

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
                    pagerState =pagerState,
                    navController = navController
                )
            }
            is HeroState.Data<*> -> {
                RowHeroes(
                    currentColor = currentColor,
                    heroes = heroesState.data as List<Hero>,
                    pagerState =pagerState,
                    navController = navController
                )
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainHorizontal(
    heroesState: HeroState,
    currentColor: MutableState<Color>,
    navController: NavController,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding(),
    ) {

        Logo(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )

        when (heroesState) {
            is HeroState.Loading -> {
                Box(modifier = modifier.weight(1f)) {
                    CircularProgressIndicator(
                        modifier = modifier
                            .size(40.dp)
                            .align(Alignment.Center),
                    )
                }
            }
            is HeroState.Error<*> -> {
                ShowAlert(message = heroesState.message)
                RowHeroes(
                    currentColor = currentColor,
                    heroes = heroesState.data as List<Hero>,
                    navController = navController,
                    pagerState = pagerState,
                    modifier = Modifier.weight(1f)
                )
            }
            is HeroState.Data<*> -> {
                RowHeroes(
                    currentColor = currentColor,
                    heroes = heroesState.data as List<Hero>,
                    navController = navController,
                    pagerState = pagerState,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}



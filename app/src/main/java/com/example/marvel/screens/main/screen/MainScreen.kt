package com.example.marvel.screens.main.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.marvel.R
import com.example.marvel.data.Hero
import com.example.marvel.data.HeroState
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
    heroesState: HeroState<List<Hero>>,
    currentColor: MutableState<Color>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = modifier.height(26.dp))

        Image(
            painter = painterResource(id = R.drawable.marvel_logo),
            contentDescription = stringResource(
                id = R.string.logo
            ),
            modifier = modifier.height(45.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = modifier.height(26.dp))

        Text(
            text = stringResource(id = R.string.choose_your_hero),
            style = MaterialTheme.typography.h4.copy(
                fontWeight = FontWeight.ExtraBold
            ),
            color = MaterialTheme.colors.onSurface
        )

        Spacer(modifier = modifier.height(26.dp))

        if (heroesState.isLoading) {
            Box {
                CircularProgressIndicator(
                    modifier = modifier
                        .size(20.dp)
                        .align(Alignment.Center)
                )
            }
        }

        if (heroesState.error != null) {
            ShowAlert(message = heroesState.error)
        }

        RowHeroes(
            currentColor = currentColor,
            heroes = heroesState.data,
            navController = navController
        )
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

        val colors = listOfNotNull(
            paletteState.vibrant,
            paletteState.darkVibrant,
            paletteState.lightVibrant,
            paletteState.muted,
            paletteState.darkMuted,
            paletteState.lightMuted,
            paletteState.dominant
        )
        colors.forEach { _ ->
            currentColor.value = colors.get(index = 3)
        }

        CardOfHero(
            currentOffset = calculateCurrentOffsetForPage(page).absoluteValue,
            navController = navController,
            hero = heroes[page]
        )
    }
}

@Composable
fun ShowAlert(message: String? = "") {
    var show = remember {
        mutableStateOf(true)
    }
    if (show.value) {
        AlertDialog(
            onDismissRequest = { show.value = false },
            title = { Text(text = message!!) },
            confirmButton = {
                Button(onClick = {
                    show.value = false
                }) {
                    Text(text = "Yes")
                }
            }
        )
    }
}

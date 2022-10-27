package com.example.marvel.screens.main.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.marvel.R
import com.github.satoshun.compose.palette.coil.rememberCoilPaletteState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import kotlin.math.absoluteValue

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = viewModel()
) {
    val heroes by mainViewModel.heroes.collectAsState()
    val currentHero by mainViewModel.currentHero.collectAsState()
    val color = remember {
        mutableStateOf(Color.Gray)
    }

    Main(
        heroes = heroes,
        size = heroes.size,
        currentHero = currentHero,
        currentColor = color,
        navController = navController,
        getCurrentHero = mainViewModel::getCurrentHero
    )
}

@Composable
fun Main(
    heroes: List<Hero>,
    size: Int,
    currentHero: Hero,
    currentColor: MutableState<Color>,
    getCurrentHero: (Int) -> Hero,
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

        RowHeroes(
            countHeroes = size,
            currentColor = currentColor,
            heroes = heroes,
            currentHero = currentHero,
            getCurrentHero = getCurrentHero,
            navController = navController
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RowHeroes(
    countHeroes: Int,
    currentColor: MutableState<Color>,
    heroes: List<Hero>,
    currentHero: Hero,
    getCurrentHero: (Int) -> Hero,
    navController: NavController
) {
    HorizontalPager(
        count = countHeroes,
        contentPadding = PaddingValues(32.dp),
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                rotate(degrees = 45f) {
                    drawRect(
                        color = currentColor.value,
                        topLeft = Offset(450f, 150f),
                        size = size / 1f
                    )
                }
            }
    ) { page ->
        getCurrentHero(currentPage)

        val paletteState = rememberCoilPaletteState(
            data = currentHero.image,
            builder = {
                crossfade(true)
                allowHardware(false)
            })

        val colors = listOf(
            paletteState.vibrant,
            paletteState.darkVibrant,
            paletteState.lightVibrant,
            paletteState.muted,
            paletteState.darkMuted,
            paletteState.lightMuted,
            paletteState.dominant
        ).filter { it != null }
        colors.forEach {
            currentColor.value = colors[3]!!
        }

        CardOfHero(
            currentOffset = calculateCurrentOffsetForPage(page).absoluteValue,
            navController = navController,
            hero = heroes[page]
        )
    }
}





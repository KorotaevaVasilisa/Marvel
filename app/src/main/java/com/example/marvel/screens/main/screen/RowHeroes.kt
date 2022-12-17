package com.example.marvel.screens.main.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.marvel.data.Hero
import com.github.satoshun.compose.palette.coil.rememberCoilPaletteState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RowHeroes(
    currentColor: MutableState<Color>,
    heroes: List<Hero>,
    navController: NavController,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {

    HorizontalPager(
        count = heroes.size,
        itemSpacing = (-7).dp,
        contentPadding = PaddingValues(40.dp),
        state = pagerState,

        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                rotate(degrees = 40f) {
                    drawRect(
                        color = currentColor.value,
                        topLeft = Offset(x = 600f, y = 500f),
                        size = size
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


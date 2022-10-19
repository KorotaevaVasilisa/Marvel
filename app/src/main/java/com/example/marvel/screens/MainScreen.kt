package com.example.marvel.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.marvel.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.marvel_logo),
            contentDescription = stringResource(
                id = R.string.logo
            )
        )
        Text(text = stringResource(id = R.string.choose_your_hero))

        HorizontalPager(
            count = info.size,
            contentPadding = PaddingValues(32.dp),
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            Card(
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                    }
                    .fillMaxWidth(),
                shape = RoundedCornerShape(15.dp)
            ) {
                val hero = info[page]
                CardOfHero(hero = hero)
            }
        }
    }
}

fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return (1 - fraction) * start + fraction * stop
}

@Preview(showBackground = true)
@Composable
fun ShowMainScreen() {
    MainScreen()
}

@Composable
fun CardOfHero(hero: Hero) {
    Box(
        Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = hero.drawable),
            contentDescription = stringResource(id = R.string.hero),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        Text(text = hero.name)
    }
}

data class Hero(
    val id: Int,
    val name: String,
    @DrawableRes val drawable: Int,
    val color: Color
)

val info = listOf<Hero>(
    Hero(
        id = 0,
        name = "Thor",
        drawable = R.drawable.thor,
        color = Color.Black
    ),
    Hero(
        id = 1,
        name = "Deadpool",
        drawable = R.drawable.deadpool,
        color = Color.Blue
    ),
    Hero(
        id = 2,
        name = "Thanos",
        drawable = R.drawable.thanos,
        color = Color.Blue
    ),
    Hero(
        id = 3,
        name = "Deadpool",
        drawable = R.drawable.deadpool,
        color = Color.Blue
    ),
)
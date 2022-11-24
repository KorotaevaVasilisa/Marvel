package com.example.marvel.screens.main.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.marvel.data.Hero
import com.example.marvel.navigation.Screen

@Composable
fun CardOfHero(
    currentOffset: Float,
    navController: NavController,
    hero: Hero,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .graphicsLayer {
                lerp(
                    start = 0.85f,
                    stop = 1f,
                    fraction = 1f - currentOffset.coerceIn(0f, 1f)
                ).also { scale ->
                    scaleX = scale
                    scaleY = scale
                }
            }
            .fillMaxWidth()
            .clickable {
                navController.navigate("${Screen.InfoScreen.route}/${hero.id}")
            },
        shape = RoundedCornerShape(15.dp)
    ) {
        HeroInfo(name = hero.name, path = hero.path, Modifier.fillMaxSize())
    }
}

fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return (1 - fraction) * start + fraction * stop
}


package com.example.marvel.screens.main.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.marvel.R
import com.example.marvel.navigation.Screen

@Composable
fun CardOfHero(
    currentOffset: Float,
    navController: NavController,
    hero: Hero,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .graphicsLayer {
                lerp(
                    start = 0.85f,
                    stop = 1f,
                    fraction = 1f - currentOffset.coerceIn(0f, 1f)
                )
                    .also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
            }
            .fillMaxWidth()
            .clickable {
                navController.navigate(Screen.InfoScreen.route + "/" + hero.id)
            },
        shape = RoundedCornerShape(15.dp)
    ) {
        Box(
            modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                model = hero.image,
                contentDescription = stringResource(R.string.hero),
                contentScale = ContentScale.Crop,
                modifier = modifier.fillMaxSize()
            )

            Text(
                text = hero.name,
                modifier = modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                maxLines = 1,
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
        }
    }
}

fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return (1 - fraction) * start + fraction * stop
}

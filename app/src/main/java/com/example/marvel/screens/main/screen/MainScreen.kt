package com.example.marvel.screens.main.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.marvel.R
import com.github.satoshun.compose.palette.coil.rememberCoilPaletteState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import kotlin.math.absoluteValue

@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel()) {
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
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
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

            Spacer(modifier = Modifier.height(26.dp))

            RowHeroes(
                countHeroes = size,
                currentColor = currentColor,
                heroes = heroes,
                currentHero = currentHero,
                getCurrentHero = getCurrentHero
            )
        }
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
        val hero = heroes[page]

        val paletteState = rememberCoilPaletteState(data = currentHero.image, builder = {
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

        Card(
            modifier = Modifier
                .graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                        .also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                }
                .fillMaxWidth(),
            shape = RoundedCornerShape(15.dp)
        ) {
            CardOfHero(hero)
        }
    }
}

fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return (1 - fraction) * start + fraction * stop
}

@Composable
fun CardOfHero(
    hero: Hero,
    modifier: Modifier = Modifier
) {
    Box(
        modifier.fillMaxSize()
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


package com.example.marvel.screens.main.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.marvel.R
import com.github.satoshun.compose.palette.coil.rememberCoilPaletteState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier, mainViewModel: MainViewModel = viewModel()) {
    val heroes = mainViewModel.heroes.collectAsState()
    val color = remember {
        mutableStateOf(Color.Gray)
    }
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

        HorizontalPager(
            count = heroes.value.size,
            contentPadding = PaddingValues(32.dp),
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    rotate(degrees = 45f) {
                        drawRect(
                            color = color.value,
                            topLeft = Offset(450f, 150f),
                            size = size / 1f
                        )
                    }
                }
        ) { page ->
            val hero = heroes.value[page]
            val paletteState = rememberCoilPaletteState(data = hero.image, builder = {
                crossfade(true)
                allowHardware(false) //IMPORTANT!
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
                color.value= colors[0]!!
            }


            Card(
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                        mainViewModel
                            .lerp(
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
                CardOfHero(hero.name, hero.image)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ShowMainScreen() {
    //  MainScreen()
}

@Composable
fun CardOfHero(
    name: String,
    image: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = image,
            contentDescription = stringResource(R.string.hero),
            contentScale = ContentScale.Crop,
            modifier = modifier.fillMaxSize()
        )

        Text(
            text = name,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            maxLines = 1,
            style = MaterialTheme.typography.h4.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )
    }
}

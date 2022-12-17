package com.example.marvel.screens.information.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.marvel.R
import com.example.marvel.data.Hero
import com.example.marvel.data.HeroState
import com.example.marvel.screens.components.ShowAlert

@Composable
fun InfoScreen(
    navHostController: NavController,
    infoViewModel: InfoViewModel = hiltViewModel(),
) {
    val orientation = LocalConfiguration.current
    when (orientation.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            when (val heroState = infoViewModel.hero.collectAsState().value) {
                is HeroState.Error<*> -> {
                    ShowAlert(message = heroState.message)
                    InfoHorizontal(navHostController, heroState.data as Hero)
                }
                is HeroState.Data<*> -> {
                    InfoHorizontal(navHostController, heroState.data as Hero)
                }
                is HeroState.Loading -> {}
            }
        }
        else -> {
            when (val heroState = infoViewModel.hero.collectAsState().value) {
                is HeroState.Error<*> -> {
                    ShowAlert(message = heroState.message)
                    InfoVertical(navHostController, heroState.data as Hero)
                }
                is HeroState.Data<*> -> {
                    InfoVertical(navHostController, heroState.data as Hero)
                }
                is HeroState.Loading -> {}
            }
        }
    }
}

@Composable
fun InfoVertical(
    navigateToInfoScreen: NavController,
    hero: Hero,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        AsyncImage(
            model = hero.path,
            contentDescription = hero.name,
            contentScale = ContentScale.Crop,
            modifier = modifier.fillMaxSize()
        )

        IconButton(
            onClick = { navigateToInfoScreen.popBackStack() },
            modifier = modifier.safeDrawingPadding()
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.back),
                modifier = modifier.size(35.dp)
            )
        }
        InfoAboutHero(
            hero.name,
            hero.description,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(20.dp)
                .windowInsetsPadding(WindowInsets.safeContent),
        )
    }
}

@Composable
fun InfoHorizontal(
    navigateToInfoScreen: NavController,
    hero: Hero,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        AsyncImage(
            model = hero.path,
            contentDescription = hero.name,
            contentScale = ContentScale.Crop,
            modifier = modifier.fillMaxSize()
        )

        IconButton(
            onClick = { navigateToInfoScreen.popBackStack() },
            modifier = modifier.windowInsetsPadding(WindowInsets.statusBars)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.back),
                modifier = modifier.size(35.dp)
            )
        }
        InfoAboutHero(
            hero.name,
            hero.description,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(20.dp)
                .windowInsetsPadding(WindowInsets.safeContent),
        )
    }
}

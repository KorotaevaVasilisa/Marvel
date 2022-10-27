package com.example.marvel.screens.information.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.marvel.R
import com.example.marvel.screens.main.screen.Hero
import com.example.marvel.screens.main.screen.MainViewModel

@Composable
fun InfoScreen(
    navHostController: NavController,
    heroId: Int?,
    mainViewModel: MainViewModel = viewModel()
) {
    val heroes by mainViewModel.heroes.collectAsState()
    val currentHero=heroes.find { it.id==heroId }
    Info(navHostController, currentHero)
}

@Composable
fun Info(navigateToInfoScreen: NavController, hero: Hero?, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        AsyncImage(
            model = hero?.image,
            contentDescription = hero?.name,
            contentScale = ContentScale.Crop,
            modifier = modifier.fillMaxSize()
        )
        IconButton(onClick = { navigateToInfoScreen.popBackStack() }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.back),
                modifier = modifier.size(35.dp)
            )
        }

        Column(
            modifier = modifier
                .align(Alignment.BottomStart)
                .padding(20.dp),
        ) {
            Text(
                text = hero?.name!!,
                maxLines = 1,
                style = MaterialTheme.typography.h3.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Spacer(modifier = modifier.height(5.dp))
            Text(
                text = hero.information,
                maxLines = 2,
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
        }

    }
}

//@Preview(showBackground = true)
//@Composable
//fun InfoScreenPreview() {
//    InfoScreen(
//        hero = Hero(
//            1,
//            "Thor",
//            "https://i.annihil.us/u/prod/marvel/i/mg/d/d0/5269657a74350.jpg",
//            "I am Thor"
//        )
//    )
//}
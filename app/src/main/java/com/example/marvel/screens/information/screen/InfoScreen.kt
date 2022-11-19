package com.example.marvel.screens.information.screen

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.marvel.R
import com.example.marvel.api.model.Hero

@Composable
fun InfoScreen(
    navHostController: NavController,
    infoViewModel: InfoViewModel = hiltViewModel()
) {
    Info(navHostController, infoViewModel.hero.collectAsState().value)
}

@Composable
fun Info(navigateToInfoScreen: NavController, hero: Hero?, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        AsyncImage(
            model = "https:${hero?.thumbnail?.path?.substringAfter(":")}.jpg",
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
                text = hero?.name.toString(),
                maxLines = 1,
                style = MaterialTheme.typography.h3.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Spacer(modifier = modifier.height(5.dp))
            Text(
                text = hero?.description.toString(),
                maxLines = 2,
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
        }
    }
}

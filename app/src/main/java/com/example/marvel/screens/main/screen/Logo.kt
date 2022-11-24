package com.example.marvel.screens.main.screen

import androidx.compose.foundation.Image
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.marvel.R

@Composable
fun Logo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.marvel_logo),
        contentDescription = stringResource(
            id = R.string.logo
        ),
        modifier = modifier,
        contentScale = ContentScale.Fit
    )

    Text(
        text = stringResource(id = R.string.choose_your_hero),
        style = MaterialTheme.typography.h5.copy(
            fontWeight = FontWeight.ExtraBold
        ),
        color = MaterialTheme.colors.onSurface
    )
}

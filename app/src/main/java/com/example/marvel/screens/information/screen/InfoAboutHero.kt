package com.example.marvel.screens.information.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun InfoAboutHero(
    name: String, description: String, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = name,
            maxLines = 1,
            style = MaterialTheme.typography.h3.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = description,
            maxLines = 2,
            style = MaterialTheme.typography.h4.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )
    }
}


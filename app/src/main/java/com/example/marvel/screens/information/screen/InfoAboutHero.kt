package com.example.marvel.screens.information.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow

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
            ),
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = "I am $name. $description",
            maxLines = 4,
            style = MaterialTheme.typography.h4.copy(
                fontWeight = FontWeight.ExtraBold
            ),
            overflow = TextOverflow.Ellipsis
        )
    }
}


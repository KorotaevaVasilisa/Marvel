package com.example.marvel.screens.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Composable
fun ShowAlert(message: String? = "") {
    var show by remember {
        mutableStateOf(true)
    }

    if (show) {
        AlertDialog(
            onDismissRequest = { show = false },
            title = { Text(text = message!!) },
            confirmButton = {
                Button(onClick = {
                    show = false
                }) {
                    Text(text = "Yes", color = Color.White)
                }
            }
        )
    }
}

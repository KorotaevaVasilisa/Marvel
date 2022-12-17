package com.example.marvel.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Gray700,
    primaryVariant = Gray600,
    background = Gray900,
    surface = Gray900,
    onPrimary = Color.White,
    secondary = Gray500,
    onBackground=Color.White,
    onSecondary = Color.White,
    onSurface = Color.White,
    error = RedErrorDark,
)

private val LightColorPalette = lightColors(
    primary = Gray700,
    primaryVariant = Gray800,
    secondary = Gray900,
    background = Gray600,
    surface = Gray600,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    error = RedErrorLight
)

@Composable
fun MarvelTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

package com.example.marvel.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.marvel.R
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior

@OptIn(ExperimentalSnapperApi::class)
@Composable
fun MainScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.marvel_logo), contentDescription = "Logo")
        Text(text = stringResource(id = R.string.choose_your_hero))
        val lazyListState: LazyListState = rememberLazyListState()

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            state = lazyListState,
            flingBehavior = rememberSnapperFlingBehavior(lazyListState),
            contentPadding = PaddingValues(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowMainScreen() {
    MainScreen()
}
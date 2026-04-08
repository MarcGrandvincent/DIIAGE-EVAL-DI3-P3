package com.rickandmortylocations.presentation.app

import androidx.compose.runtime.Composable
import com.rickandmortylocations.presentation.di.PresentationModule

@Composable
actual fun RickAndMortyDesktopApp(module: PresentationModule) {
    RickAndMortyMobileApp(module)
}


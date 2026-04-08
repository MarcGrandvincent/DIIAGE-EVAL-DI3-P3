package com.rickandmortylocations.presentation.app

import androidx.compose.runtime.Composable
import com.rickandmortylocations.presentation.di.PresentationModule

@Composable
expect fun RickAndMortyDesktopApp(module: PresentationModule)


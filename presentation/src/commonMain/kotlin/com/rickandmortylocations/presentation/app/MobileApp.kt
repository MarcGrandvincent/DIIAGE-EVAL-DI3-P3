package com.rickandmortylocations.presentation.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.rickandmortylocations.presentation.detail.LocationDetailUiAction
import com.rickandmortylocations.presentation.di.PresentationModule
import com.rickandmortylocations.presentation.list.LocationListUiAction
import com.rickandmortylocations.presentation.navigation.MobileRoute
import com.rickandmortylocations.presentation.screen.LocationDetailScreen
import com.rickandmortylocations.presentation.screen.LocationListScreen
import com.rickandmortylocations.presentation.theme.RickAndMortyTheme

@Composable
fun RickAndMortyMobileApp(module: PresentationModule) {
    val route by module.navigator.route.collectAsState()
    val listState by module.listViewModel.uiState.collectAsState()
    val detailState by module.detailViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        module.listViewModel.onAction(LocationListUiAction.Load)
    }

    RickAndMortyTheme {
        when (val current = route) {
            MobileRoute.List -> LocationListScreen(
                state = listState,
                onAction = module.listViewModel::onAction,
                onLocationClick = { id ->
                    module.audioManager.playSelectSound()
                    // Navigate first; detail loading starts once the detail screen is displayed.
                    module.navigator.toDetail(id)
                }
            )

            is MobileRoute.Detail -> {
                LaunchedEffect(current.locationId) {
                    module.detailViewModel.onAction(LocationDetailUiAction.Load(current.locationId))
                }
                LocationDetailScreen(
                    state = detailState,
                    onAction = module.detailViewModel::onAction,
                    onBack = module.navigator::back
                )
            }
        }
    }
}

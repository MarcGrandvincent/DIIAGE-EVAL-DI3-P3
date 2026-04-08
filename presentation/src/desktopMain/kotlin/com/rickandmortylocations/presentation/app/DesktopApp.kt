package com.rickandmortylocations.presentation.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rickandmortylocations.presentation.detail.LocationDetailUiAction
import com.rickandmortylocations.presentation.di.PresentationModule
import com.rickandmortylocations.presentation.list.LocationListUiAction
import com.rickandmortylocations.presentation.screen.LocationDetailScreen
import com.rickandmortylocations.presentation.screen.LocationListScreen
import com.rickandmortylocations.presentation.theme.RickAndMortyTheme

@Composable
actual fun RickAndMortyDesktopApp(module: PresentationModule) {
    val listState by module.listViewModel.uiState.collectAsState()
    val detailState by module.detailViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        module.listViewModel.onAction(LocationListUiAction.Load)
    }

    RickAndMortyTheme {
        Row(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(modifier = Modifier.weight(0.45f).fillMaxSize()) {
                LocationListScreen(
                    state = listState,
                    onAction = module.listViewModel::onAction,
                    onLocationClick = { id ->
                        module.audioManager.playSelectSound()
                        module.detailViewModel.onAction(LocationDetailUiAction.Load(id))
                    }
                )
            }
            Card(modifier = Modifier.weight(0.55f).fillMaxSize()) {
                if (detailState.item == null && !detailState.isLoading && detailState.errorMessage == null) {
                    Text(
                        text = "Selectionnez une location dans la liste",
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    )
                } else {
                    LocationDetailScreen(
                        state = detailState,
                        onAction = module.detailViewModel::onAction,
                        onBack = null
                    )
                }
            }
        }
    }
}

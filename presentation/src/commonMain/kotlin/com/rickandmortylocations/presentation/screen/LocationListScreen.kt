package com.rickandmortylocations.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rickandmortylocations.domain.model.Location
import com.rickandmortylocations.presentation.list.LocationListUiAction
import com.rickandmortylocations.presentation.list.LocationListUiState

@Composable
fun LocationListScreen(
    state: LocationListUiState,
    onAction: (LocationListUiAction) -> Unit,
    onLocationClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Rick and Morty Locations",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "${state.items.size} / ${state.totalCount.coerceAtLeast(state.items.size)} locations",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (state.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        if (state.errorMessage != null) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Erreur: ${state.errorMessage}")
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = { onAction(LocationListUiAction.Retry) }) {
                            Text("Reessayer")
                        }
                        OutlinedButton(onClick = { onAction(LocationListUiAction.Refresh) }) {
                            Text("Rafraichir")
                        }
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(state.items, key = { it.id }) { location ->
                LocationItemCard(location = location, onLocationClick = onLocationClick)
            }

            item {
                if (state.isAppending) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (state.hasNextPage) {
                    OutlinedButton(
                        onClick = { onAction(LocationListUiAction.LoadNextPage) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Charger la page suivante (${state.currentPage + 1}/${state.totalPages})")
                    }
                }
            }
        }
    }
}

@Composable
private fun LocationItemCard(
    location: Location,
    onLocationClick: (Int) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onLocationClick(location.id) }
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(location.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text("Type: ${location.type}", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Dimension: ${location.dimension}", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Residents: ${location.residentsCount}", color = MaterialTheme.colorScheme.primary)
        }
    }
}

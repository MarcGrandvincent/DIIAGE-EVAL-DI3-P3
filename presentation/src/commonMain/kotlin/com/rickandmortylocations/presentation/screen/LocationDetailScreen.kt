package com.rickandmortylocations.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.rickandmortylocations.presentation.detail.LocationDetailUiAction
import com.rickandmortylocations.presentation.detail.LocationDetailUiState

@Composable
fun LocationDetailScreen(
    state: LocationDetailUiState,
    onAction: (LocationDetailUiAction) -> Unit,
    onBack: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        onBack?.let { backAction ->
            OutlinedButton(onClick = backAction, modifier = Modifier.fillMaxWidth()) {
                Text("Retour")
            }
        }

        when {
            state.isLoading -> CircularProgressIndicator()
            state.errorMessage != null -> {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Erreur: ${state.errorMessage}")
                        Button(onClick = { onAction(LocationDetailUiAction.Retry) }) {
                            Text("Reessayer")
                        }
                    }
                }
            }

            state.item != null -> {
                val item = state.item
                Text(item.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                AssistChip(onClick = {}, label = { Text(item.type) })
                Text("Dimension: ${item.dimension}")
                Text("Residents count: ${item.residentsCount}")
                Text("Created: ${item.createdAtIso.substringBefore('T')}")
                Text("API URL: ${item.apiUrl}", color = MaterialTheme.colorScheme.primary)

                Text(
                    text = "Residents",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 6.dp)
                )

                if (state.isResidentsLoading) {
                    CircularProgressIndicator()
                }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (!state.isResidentsLoading && state.residents.isEmpty()) {
                        item {
                            Text("Aucun resident charge pour cette location.")
                        }
                    } else {
                        items(state.residents) { resident ->
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    AsyncImage(
                                        model = resident.imageUrl,
                                        contentDescription = resident.name,
                                        modifier = Modifier
                                            .size(56.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                    )
                                    Column {
                                        Text(resident.name, fontWeight = FontWeight.SemiBold)
                                        Text(
                                            "${resident.status} - ${resident.species}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

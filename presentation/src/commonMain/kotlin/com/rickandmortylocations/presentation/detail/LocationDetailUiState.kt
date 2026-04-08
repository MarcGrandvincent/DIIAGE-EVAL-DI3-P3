package com.rickandmortylocations.presentation.detail

import com.rickandmortylocations.domain.model.Location
import com.rickandmortylocations.domain.model.Resident

data class LocationDetailUiState(
    val isLoading: Boolean = false,
    val item: Location? = null,
    val errorMessage: String? = null,
    val lastRequestedId: Int? = null,
    val residents: List<Resident> = emptyList(),
    val isResidentsLoading: Boolean = false
)

package com.rickandmortylocations.presentation.list

import com.rickandmortylocations.domain.model.Location

data class LocationListUiState(
    val isLoading: Boolean = false,
    val isAppending: Boolean = false,
    val items: List<Location> = emptyList(),
    val errorMessage: String? = null,
    val currentPage: Int = 1,
    val totalPages: Int = 1,
    val totalCount: Int = 0,
    val hasNextPage: Boolean = false
)


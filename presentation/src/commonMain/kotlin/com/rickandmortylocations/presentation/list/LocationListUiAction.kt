package com.rickandmortylocations.presentation.list

sealed interface LocationListUiAction {
    data object Load : LocationListUiAction
    data object Retry : LocationListUiAction
    data object Refresh : LocationListUiAction
    data object LoadNextPage : LocationListUiAction
}

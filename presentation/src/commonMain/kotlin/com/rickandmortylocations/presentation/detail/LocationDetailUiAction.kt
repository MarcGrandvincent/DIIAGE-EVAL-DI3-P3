package com.rickandmortylocations.presentation.detail

sealed interface LocationDetailUiAction {
    data class Load(val id: Int) : LocationDetailUiAction
    data object Retry : LocationDetailUiAction
}

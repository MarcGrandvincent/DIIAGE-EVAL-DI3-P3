package com.rickandmortylocations.presentation.navigation

sealed interface MobileRoute {
    data object List : MobileRoute
    data class Detail(val locationId: Int) : MobileRoute
}


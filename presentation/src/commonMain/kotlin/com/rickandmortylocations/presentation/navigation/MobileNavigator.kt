package com.rickandmortylocations.presentation.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MobileNavigator {
    private val stack = mutableListOf<MobileRoute>(MobileRoute.List)
    private val _route = MutableStateFlow<MobileRoute>(MobileRoute.List)
    val route: StateFlow<MobileRoute> = _route.asStateFlow()

    fun toDetail(locationId: Int) {
        val detail = MobileRoute.Detail(locationId)
        stack.add(detail)
        _route.value = detail
    }

    fun back() {
        if (stack.size == 1) return
        stack.removeAt(stack.lastIndex)
        _route.value = stack.last()
    }
}


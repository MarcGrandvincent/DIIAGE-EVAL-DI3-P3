package com.rickandmortylocations.presentation.list

import com.rickandmortylocations.domain.usecase.GetLocationsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocationListViewModel(
    private val getLocationsUseCase: GetLocationsUseCase
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val _uiState = MutableStateFlow(LocationListUiState())
    val uiState: StateFlow<LocationListUiState> = _uiState.asStateFlow()

    fun onAction(action: LocationListUiAction) {
        when (action) {
            LocationListUiAction.Load,
            LocationListUiAction.Retry,
            LocationListUiAction.Refresh -> loadPage(1, reset = true)

            LocationListUiAction.LoadNextPage -> {
                val state = _uiState.value
                if (!state.hasNextPage || state.isAppending || state.isLoading) return
                loadPage(page = state.currentPage + 1, reset = false)
            }
        }
    }

    private fun loadPage(page: Int, reset: Boolean) {
        scope.launch {
            val previous = _uiState.value
            _uiState.value = if (reset) {
                previous.copy(isLoading = true, errorMessage = null)
            } else {
                previous.copy(isAppending = true, errorMessage = null)
            }

            val result = getLocationsUseCase(page)
            _uiState.value = result.fold(
                onSuccess = { locationPage ->
                    val mergedItems = if (reset) locationPage.items else previous.items + locationPage.items
                    previous.copy(
                        isLoading = false,
                        isAppending = false,
                        items = mergedItems,
                        errorMessage = null,
                        currentPage = page,
                        totalPages = locationPage.info.pages,
                        totalCount = locationPage.info.count,
                        hasNextPage = locationPage.info.nextPage != null
                    )
                },
                onFailure = { throwable ->
                    previous.copy(
                        isLoading = false,
                        isAppending = false,
                        errorMessage = throwable.message ?: "Unknown error"
                    )
                }
            )
        }
    }
}

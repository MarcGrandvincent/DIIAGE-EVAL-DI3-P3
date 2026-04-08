package com.rickandmortylocations.presentation.detail

import com.rickandmortylocations.domain.usecase.GetLocationDetailUseCase
import com.rickandmortylocations.domain.usecase.GetLocationResidentsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocationDetailViewModel(
    private val getLocationDetailUseCase: GetLocationDetailUseCase,
    private val getLocationResidentsUseCase: GetLocationResidentsUseCase
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val _uiState = MutableStateFlow(LocationDetailUiState())
    val uiState: StateFlow<LocationDetailUiState> = _uiState.asStateFlow()

    fun onAction(action: LocationDetailUiAction) {
        when (action) {
            is LocationDetailUiAction.Load -> load(action.id)
            LocationDetailUiAction.Retry -> _uiState.value.lastRequestedId?.let { load(it) }
        }
    }

    private fun load(id: Int) {
        scope.launch {
            val previous = _uiState.value
            _uiState.value = previous.copy(
                isLoading = true,
                errorMessage = null,
                lastRequestedId = id,
                isResidentsLoading = true,
                residents = emptyList()
            )
            val result = getLocationDetailUseCase(id)
            _uiState.value = result.fold(
                onSuccess = { item ->
                    LocationDetailUiState(
                        isLoading = false,
                        item = item,
                        errorMessage = null,
                        lastRequestedId = id,
                        residents = emptyList(),
                        isResidentsLoading = true
                    )
                },
                onFailure = { throwable ->
                    previous.copy(
                        isLoading = false,
                        errorMessage = throwable.message ?: "Unknown error",
                        lastRequestedId = id,
                        isResidentsLoading = false
                    )
                }
            )

            val loaded = _uiState.value.item ?: return@launch
            val residentsResult = getLocationResidentsUseCase(loaded.residentsIds)
            _uiState.value = residentsResult.fold(
                onSuccess = { residents ->
                    _uiState.value.copy(residents = residents, isResidentsLoading = false)
                },
                onFailure = {
                    _uiState.value.copy(isResidentsLoading = false)
                }
            )
        }
    }
}

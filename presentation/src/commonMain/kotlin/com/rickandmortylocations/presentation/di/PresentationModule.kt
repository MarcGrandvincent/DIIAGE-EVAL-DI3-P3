package com.rickandmortylocations.presentation.di

import com.rickandmortylocations.domain.repository.LocationRepository
import com.rickandmortylocations.domain.usecase.GetLocationDetailUseCase
import com.rickandmortylocations.domain.usecase.GetLocationResidentsUseCase
import com.rickandmortylocations.domain.usecase.GetLocationsUseCase
import com.rickandmortylocations.presentation.audio.AudioManager
import com.rickandmortylocations.presentation.detail.LocationDetailViewModel
import com.rickandmortylocations.presentation.list.LocationListViewModel
import com.rickandmortylocations.presentation.navigation.MobileNavigator

class PresentationModule(
    repository: LocationRepository,
    val audioManager: AudioManager,
    val navigator: MobileNavigator = MobileNavigator()
) {
    private val getLocationsUseCase = GetLocationsUseCase(repository)
    private val getLocationDetailUseCase = GetLocationDetailUseCase(repository)
    private val getLocationResidentsUseCase = GetLocationResidentsUseCase(repository)

    val listViewModel: LocationListViewModel = LocationListViewModel(getLocationsUseCase)
    val detailViewModel: LocationDetailViewModel = LocationDetailViewModel(
        getLocationDetailUseCase,
        getLocationResidentsUseCase
    )
}

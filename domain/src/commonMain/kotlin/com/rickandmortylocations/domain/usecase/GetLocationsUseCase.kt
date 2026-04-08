package com.rickandmortylocations.domain.usecase

import com.rickandmortylocations.domain.model.LocationPage
import com.rickandmortylocations.domain.repository.LocationRepository

class GetLocationsUseCase(
    private val repository: LocationRepository
) {
    suspend operator fun invoke(page: Int = 1): Result<LocationPage> {
        val safePage = if (page < 1) 1 else page
        return repository.getLocations(safePage)
    }
}

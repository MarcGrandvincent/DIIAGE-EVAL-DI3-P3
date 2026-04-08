package com.rickandmortylocations.domain.usecase

import com.rickandmortylocations.domain.model.Location
import com.rickandmortylocations.domain.repository.LocationRepository

class GetLocationDetailUseCase(
    private val repository: LocationRepository
) {
    suspend operator fun invoke(id: Int): Result<Location> {
        if (id <= 0) return Result.failure(IllegalArgumentException("Location id must be > 0"))
        return repository.getLocationById(id)
    }
}


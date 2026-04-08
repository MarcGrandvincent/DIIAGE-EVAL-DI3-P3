package com.rickandmortylocations.domain.usecase

import com.rickandmortylocations.domain.model.Resident
import com.rickandmortylocations.domain.repository.LocationRepository

class GetLocationResidentsUseCase(
    private val repository: LocationRepository
) {
    suspend operator fun invoke(ids: List<Int>): Result<List<Resident>> {
        if (ids.isEmpty()) return Result.success(emptyList())
        return repository.getResidentsByIds(ids)
    }
}


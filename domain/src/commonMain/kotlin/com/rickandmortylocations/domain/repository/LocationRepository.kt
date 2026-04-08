package com.rickandmortylocations.domain.repository

import com.rickandmortylocations.domain.model.Location
import com.rickandmortylocations.domain.model.LocationPage
import com.rickandmortylocations.domain.model.Resident

interface LocationRepository {
    suspend fun getLocations(page: Int = 1): Result<LocationPage>
    suspend fun getLocationById(id: Int): Result<Location>
    suspend fun getResidentsByIds(ids: List<Int>): Result<List<Resident>>
}

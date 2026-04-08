package com.rickandmortylocations.data.local

import com.rickandmortylocations.data.local.entity.LocationEntity

interface LocationLocalDataSource {
    suspend fun getLocations(): List<LocationEntity>
    suspend fun getLocationById(id: Int): LocationEntity?
    suspend fun saveLocations(locations: List<LocationEntity>)
    suspend fun saveLocation(location: LocationEntity)
}


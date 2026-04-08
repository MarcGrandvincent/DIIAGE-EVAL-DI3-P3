package com.rickandmortylocations.data.local

import com.rickandmortylocations.data.local.entity.LocationEntity
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class InMemoryLocationLocalDataSource : LocationLocalDataSource {
    private val cache = mutableMapOf<Int, LocationEntity>()
    private val mutex = Mutex()

    override suspend fun getLocations(): List<LocationEntity> = mutex.withLock { cache.values.toList() }

    override suspend fun getLocationById(id: Int): LocationEntity? = mutex.withLock { cache[id] }

    override suspend fun saveLocations(locations: List<LocationEntity>) {
        mutex.withLock {
            locations.forEach { location -> cache[location.id] = location }
        }
    }

    override suspend fun saveLocation(location: LocationEntity) {
        mutex.withLock { cache[location.id] = location }
    }
}


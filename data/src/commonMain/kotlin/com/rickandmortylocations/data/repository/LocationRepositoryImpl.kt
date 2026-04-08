package com.rickandmortylocations.data.repository

import com.rickandmortylocations.data.local.LocationLocalDataSource
import com.rickandmortylocations.data.mapper.toDomain
import com.rickandmortylocations.data.mapper.toEntity
import com.rickandmortylocations.data.remote.LocationRemoteDataSource
import com.rickandmortylocations.domain.model.Location
import com.rickandmortylocations.domain.model.LocationPage
import com.rickandmortylocations.domain.model.PageInfo
import com.rickandmortylocations.domain.model.Resident
import com.rickandmortylocations.domain.repository.LocationRepository

class LocationRepositoryImpl(
    private val remoteDataSource: LocationRemoteDataSource,
    private val localDataSource: LocationLocalDataSource
) : LocationRepository {

    override suspend fun getLocations(page: Int): Result<LocationPage> = runCatching {
        val remote = remoteDataSource.getLocations(page)
        val entities = remote.results.map { dto -> dto.toEntity() }
        localDataSource.saveLocations(entities)
        LocationPage(
            info = remote.info.toDomain(),
            items = entities.map { entity -> entity.toDomain() }
        )
    }.recoverCatching {
        val cached = localDataSource.getLocations()
        if (cached.isEmpty()) throw IllegalStateException("No data in local cache")
        LocationPage(
            info = PageInfo(
                count = cached.size,
                pages = 1,
                nextPage = null,
                previousPage = null
            ),
            items = cached.map { entity -> entity.toDomain() }
        )
    }

    override suspend fun getLocationById(id: Int): Result<Location> = runCatching {
        val remote = remoteDataSource.getLocationById(id)
        val entity = remote.toEntity()
        localDataSource.saveLocation(entity)
        entity.toDomain()
    }.recoverCatching {
        localDataSource.getLocationById(id)?.toDomain()
            ?: throw IllegalStateException("Location $id not found")
    }

    override suspend fun getResidentsByIds(ids: List<Int>): Result<List<Resident>> = runCatching {
        ids.distinct().take(20).map { residentId ->
            remoteDataSource.getCharacterById(residentId).toDomain()
        }
    }
}

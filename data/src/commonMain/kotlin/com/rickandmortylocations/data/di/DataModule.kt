package com.rickandmortylocations.data.di

import com.rickandmortylocations.data.local.InMemoryLocationLocalDataSource
import com.rickandmortylocations.data.remote.LocationRemoteDataSource
import com.rickandmortylocations.data.repository.LocationRepositoryImpl
import com.rickandmortylocations.domain.repository.LocationRepository
import io.ktor.client.HttpClient

class DataModule(httpClient: HttpClient) {
    private val localDataSource = InMemoryLocationLocalDataSource()
    private val remoteDataSource = LocationRemoteDataSource(httpClient)

    val locationRepository: LocationRepository = LocationRepositoryImpl(
        remoteDataSource = remoteDataSource,
        localDataSource = localDataSource
    )
}


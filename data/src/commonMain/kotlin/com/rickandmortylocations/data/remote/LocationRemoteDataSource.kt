package com.rickandmortylocations.data.remote

import com.rickandmortylocations.data.remote.dto.CharacterDto
import com.rickandmortylocations.data.remote.dto.LocationDto
import com.rickandmortylocations.data.remote.dto.LocationsResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class LocationRemoteDataSource(
    private val httpClient: HttpClient,
    private val baseUrl: String = "https://rickandmortyapi.com/api"
) {
    suspend fun getLocations(page: Int): LocationsResponseDto {
        return httpClient.get("$baseUrl/location") {
            parameter("page", page)
        }.body()
    }

    suspend fun getLocationById(id: Int): LocationDto {
        return httpClient.get("$baseUrl/location/$id").body()
    }

    suspend fun getCharacterById(id: Int): CharacterDto {
        return httpClient.get("$baseUrl/character/$id").body()
    }
}

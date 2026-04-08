package com.rickandmortylocations.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationsResponseDto(
    @SerialName("info") val info: LocationsInfoDto,
    @SerialName("results") val results: List<LocationDto>
)

@Serializable
data class LocationsInfoDto(
    @SerialName("count") val count: Int,
    @SerialName("pages") val pages: Int,
    @SerialName("next") val next: String?,
    @SerialName("prev") val prev: String?
)

@Serializable
data class LocationDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("type") val type: String,
    @SerialName("dimension") val dimension: String,
    @SerialName("residents") val residents: List<String>,
    @SerialName("url") val url: String,
    @SerialName("created") val created: String
)

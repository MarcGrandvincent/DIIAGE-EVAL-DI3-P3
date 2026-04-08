package com.rickandmortylocations.data.mapper

import com.rickandmortylocations.data.local.entity.LocationEntity
import com.rickandmortylocations.data.remote.dto.CharacterDto
import com.rickandmortylocations.data.remote.dto.LocationDto
import com.rickandmortylocations.data.remote.dto.LocationsInfoDto
import com.rickandmortylocations.domain.model.Location
import com.rickandmortylocations.domain.model.PageInfo
import com.rickandmortylocations.domain.model.Resident

fun LocationDto.toEntity(): LocationEntity = LocationEntity(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residentsIds = residents.mapNotNull { url -> url.substringAfterLast('/').toIntOrNull() },
    residentsUrls = residents,
    apiUrl = url,
    createdAtIso = created
)

fun LocationEntity.toDomain(): Location = Location(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residentsIds = residentsIds,
    residentsUrls = residentsUrls,
    apiUrl = apiUrl,
    createdAtIso = createdAtIso
)

fun LocationDto.toDomain(): Location = toEntity().toDomain()

fun LocationsInfoDto.toDomain(): PageInfo = PageInfo(
    count = count,
    pages = pages,
    nextPage = next?.let { value -> value.substringAfter("page=", "").toIntOrNull() },
    previousPage = prev?.let { value -> value.substringAfter("page=", "").toIntOrNull() }
)

fun CharacterDto.toDomain(): Resident = Resident(
    id = id,
    name = name,
    imageUrl = image,
    status = status,
    species = species
)

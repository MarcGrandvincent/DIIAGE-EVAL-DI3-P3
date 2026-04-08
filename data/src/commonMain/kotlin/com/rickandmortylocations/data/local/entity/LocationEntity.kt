package com.rickandmortylocations.data.local.entity

data class LocationEntity(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residentsIds: List<Int>,
    val residentsUrls: List<String>,
    val apiUrl: String,
    val createdAtIso: String
)

package com.rickandmortylocations.domain.model

data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residentsIds: List<Int>,
    val residentsUrls: List<String>,
    val apiUrl: String,
    val createdAtIso: String
) {
    val residentsCount: Int = residentsIds.size
}

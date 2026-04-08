package com.rickandmortylocations.domain.model

data class Resident(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val status: String,
    val species: String
)

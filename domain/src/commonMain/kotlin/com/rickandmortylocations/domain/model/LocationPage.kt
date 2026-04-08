package com.rickandmortylocations.domain.model

data class LocationPage(
    val info: PageInfo,
    val items: List<Location>
)

data class PageInfo(
    val count: Int,
    val pages: Int,
    val nextPage: Int?,
    val previousPage: Int?
)

package com.pouyaheydari.android.core.domain

data class AlbumResponse(val albums: List<Album>, val totalResults: Int)

data class Album(
    val id: Int?,
    val albumName: String,
    val artistName: String,
    var image: String,
)

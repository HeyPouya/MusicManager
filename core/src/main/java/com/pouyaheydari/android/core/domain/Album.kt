package com.pouyaheydari.android.core.domain

data class AlbumResponse(val albums: List<Album>, val totalResults: Int)

data class Album(
    // Unfortunately the only unique id that we can get from last.fm api is mbid String so we have to use it as our primary key
    val id: Int?,
    val albumName: String,
    val artistName: String,
    var image: String,
)

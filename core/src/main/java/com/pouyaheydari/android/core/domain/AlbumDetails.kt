package com.pouyaheydari.android.core.domain

data class AlbumDetails(
    val artist: String,
    val image: String,
    val name: String,
    val tracks: List<Track>,
)

data class Track(val id: Int?, val name: String, var albumId: Long?)
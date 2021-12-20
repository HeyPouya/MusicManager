package com.pouyaheydari.android.core.domain

data class AlbumDetails(
    val artist: String,
    val image: String,
    val mbid: String,
    val name: String,
    val tracks: List<Track>,
)

data class Track(val id: Int?, val name: String, val albumId: String)
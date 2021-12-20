package com.pouyaheydari.android.core.data

import javax.inject.Inject

class ArtistRepository @Inject constructor(private val artistDataSource: ArtistDataSource) {

    suspend fun findArtist(artist: String, pageNumber: Int) =
        artistDataSource.findArtist(artist, pageNumber)

    suspend fun getTopAlbumsByArtist(artist: String, pageNumber: Int) =
        artistDataSource.getTopAlbumsByArtist(artist, pageNumber)

    suspend fun getAlbumDetails(artist: String, albumName: String) =
        artistDataSource.getAlbumDetails(artist, albumName)
}
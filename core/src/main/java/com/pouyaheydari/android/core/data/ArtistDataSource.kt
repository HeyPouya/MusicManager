package com.pouyaheydari.android.core.data

import com.pouyaheydari.android.core.domain.*

interface ArtistDataSource {

    suspend fun findArtist(artist: String, pageNumber: Int): ArtistResponse

    suspend fun getTopAlbumsByArtist(artist: String, pageNumber: Int): AlbumResponse

    suspend fun getAlbumDetails(artist: String, albumName: String): AlbumDetails

}
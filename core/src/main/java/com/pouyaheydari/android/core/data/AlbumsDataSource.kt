package com.pouyaheydari.android.core.data

import com.pouyaheydari.android.core.domain.Album
import com.pouyaheydari.android.core.domain.AlbumDetails
import com.pouyaheydari.android.core.domain.AlbumResponse
import com.pouyaheydari.android.core.domain.Track

interface AlbumsDataSource {

    suspend fun saveAlbum(album: Album):Long

    suspend fun getAllAlbums(): AlbumResponse

    suspend fun saveTracks(vararg tracks: Track)

    suspend fun getAllTracks(): List<Track>

    suspend fun getSpecificAlbum(artistName: String, albumName: String): AlbumDetails

    suspend fun doesAlbumExists(artistName: String, albumName: String): Boolean

    suspend fun removeAlbum(artistName: String, albumName: String)
}
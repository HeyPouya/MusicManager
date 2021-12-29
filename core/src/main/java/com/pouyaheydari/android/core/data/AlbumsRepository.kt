package com.pouyaheydari.android.core.data

import com.pouyaheydari.android.core.domain.Album
import com.pouyaheydari.android.core.domain.Track
import javax.inject.Inject

class AlbumsRepository @Inject constructor(private val albumsDataSource: AlbumsDataSource) {

    suspend fun saveAlbum(album: Album) = albumsDataSource.saveAlbum(album)

    suspend fun getAllAlbums() = albumsDataSource.getAllAlbums()

    suspend fun saveTracks(vararg tracks: Track) = albumsDataSource.saveTracks(*tracks)

    suspend fun getSpecificAlbum(artistName: String, albumName: String) =
        albumsDataSource.getSpecificAlbum(artistName, albumName)

    suspend fun doesAlbumExists(artistName: String, albumName: String) =
        albumsDataSource.doesAlbumExists(artistName, albumName)

    suspend fun removeAlbum(artistName: String, albumName: String) =
        albumsDataSource.removeAlbum(artistName, albumName)

}
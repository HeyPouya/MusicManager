package com.pouyaheydari.android.core.interctors

import com.pouyaheydari.android.core.data.AlbumsRepository
import com.pouyaheydari.android.core.domain.Album
import com.pouyaheydari.android.core.domain.AlbumDetails
import javax.inject.Inject

class SaveAlbum @Inject constructor(private val albumsRepository: AlbumsRepository) {
    suspend operator fun invoke(album: AlbumDetails) {
        val albumId = albumsRepository.saveAlbum(Album(null, album.name, album.artist, album.image))
        val tracks = album.tracks.onEach { it.albumId = albumId }
        albumsRepository.saveTracks(*tracks.toTypedArray())
    }
}
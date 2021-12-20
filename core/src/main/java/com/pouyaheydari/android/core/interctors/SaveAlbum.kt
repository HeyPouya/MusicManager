package com.pouyaheydari.android.core.interctors

import com.pouyaheydari.android.core.data.AlbumsRepository
import com.pouyaheydari.android.core.domain.Album
import com.pouyaheydari.android.core.domain.AlbumDetails
import javax.inject.Inject

class SaveAlbum @Inject constructor(private val albumsRepository: AlbumsRepository) {
    suspend operator fun invoke(album: AlbumDetails) {
        albumsRepository.saveAlbum(Album(album.mbid, album.name, album.artist, album.image))
        albumsRepository.saveTracks(*album.tracks.toTypedArray())
    }
}
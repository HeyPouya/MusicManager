package com.pouyaheydari.android.core.interctors

import com.pouyaheydari.android.core.data.AlbumsRepository
import javax.inject.Inject

class DoesAlbumExists @Inject constructor(private val albumsRepository: AlbumsRepository) {
    suspend operator fun invoke(artistName: String, albumName: String) =
        albumsRepository.doesAlbumExists(artistName, albumName)
}
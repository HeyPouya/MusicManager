package com.pouyaheydari.android.core.interctors

import com.pouyaheydari.android.core.data.AlbumsRepository
import javax.inject.Inject

class GetSpecificAlbums @Inject constructor(private val albumsRepository: AlbumsRepository) {
    suspend operator fun invoke(artistName: String, albumName: String) =
        albumsRepository.getSpecificAlbum(artistName, albumName)
}
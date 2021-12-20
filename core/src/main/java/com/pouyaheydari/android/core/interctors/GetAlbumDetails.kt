package com.pouyaheydari.android.core.interctors

import com.pouyaheydari.android.core.data.AlbumsRepository
import com.pouyaheydari.android.core.data.ArtistRepository
import javax.inject.Inject

class GetAlbumDetails @Inject constructor(
    private val artistRepository: ArtistRepository,
    private val albumsRepository: AlbumsRepository
) {
    suspend operator fun invoke(artistName: String, albumName: String) =
        if (albumsRepository.doesAlbumExists(artistName, albumName))
            albumsRepository.getSpecificAlbum(artistName, albumName)
        else
            artistRepository.getAlbumDetails(artistName, albumName)
}
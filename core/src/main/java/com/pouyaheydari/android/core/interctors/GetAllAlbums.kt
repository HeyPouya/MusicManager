package com.pouyaheydari.android.core.interctors

import com.pouyaheydari.android.core.data.AlbumsRepository
import javax.inject.Inject

class GetAllAlbums @Inject constructor(private val albumsRepository: AlbumsRepository) {
    suspend operator fun invoke() = albumsRepository.getAllAlbums()
}
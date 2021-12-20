package com.pouyaheydari.android.core.interctors

import com.pouyaheydari.android.core.data.AlbumsRepository
import com.pouyaheydari.android.core.domain.Album
import javax.inject.Inject

class GetAllTracks @Inject constructor(private val albumsRepository: AlbumsRepository) {
    suspend operator fun invoke() = albumsRepository.getAllTracks()
}
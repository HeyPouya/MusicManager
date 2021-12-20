package com.pouyaheydari.android.core.interctors

import com.pouyaheydari.android.core.data.AlbumsRepository
import com.pouyaheydari.android.core.domain.Track
import javax.inject.Inject

class SaveTracks @Inject constructor(private val albumsRepository: AlbumsRepository) {
    suspend operator fun invoke(vararg tracks: Track) = albumsRepository.saveTracks(*tracks)
}
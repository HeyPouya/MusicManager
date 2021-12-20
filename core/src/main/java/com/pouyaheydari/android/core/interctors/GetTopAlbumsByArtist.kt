package com.pouyaheydari.android.core.interctors

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.pouyaheydari.android.core.data.ArtistRepository
import com.pouyaheydari.android.core.data.NETWORK_PAGE_SIZE
import com.pouyaheydari.android.core.data.TopAlbumsPagingSource
import javax.inject.Inject

class GetTopAlbumsByArtist @Inject constructor(private val artistRepository: ArtistRepository) {
    operator fun invoke(artistName: String) = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { TopAlbumsPagingSource(artistRepository, artistName) }
    ).flow
}
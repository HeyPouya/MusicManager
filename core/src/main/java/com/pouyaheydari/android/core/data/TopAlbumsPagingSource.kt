package com.pouyaheydari.android.core.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pouyaheydari.android.core.domain.Album

private const val STARTING_PAGE = 1

class TopAlbumsPagingSource(
    private val artistRepository: ArtistRepository,
    private val artistName: String
) : PagingSource<Int, Album>() {

    override fun getRefreshKey(state: PagingState<Int, Album>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Album> {
        val position = params.key ?: STARTING_PAGE
        return try {
            val response = artistRepository.getTopAlbumsByArtist(artistName, position)
            val total = response.totalResults

            val nextKey = if (NETWORK_PAGE_SIZE * position < total)
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            else
                null
            LoadResult.Page(
                data = response.albums,
                prevKey = if (position == STARTING_PAGE) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            exception.printStackTrace()
            return LoadResult.Error(exception)
        }
    }
}
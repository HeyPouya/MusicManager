package com.pouyaheydari.android.core.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pouyaheydari.android.core.domain.Artist

private const val STARTING_PAGE = 1
const val NETWORK_PAGE_SIZE = 30

class SearchArtistPagingSource(
    private val repository: ArtistRepository,
    private val artistName: String
) : PagingSource<Int, Artist>() {

    override fun getRefreshKey(state: PagingState<Int, Artist>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artist> {
        val position = params.key ?: STARTING_PAGE
        return try {
            val response = repository.findArtist(artistName, position)

            val nextKey =
                if (NETWORK_PAGE_SIZE * position < response.totalResult)
                    position + (params.loadSize / NETWORK_PAGE_SIZE)
                else
                    null
            LoadResult.Page(
                data = response.artists,
                prevKey = if (position == STARTING_PAGE) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            exception.printStackTrace()
            return LoadResult.Error(exception)
        }
    }
}
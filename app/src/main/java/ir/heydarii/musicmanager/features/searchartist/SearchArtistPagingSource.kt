package ir.heydarii.musicmanager.features.searchartist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ir.heydarii.musicmanager.pojos.searchartist.Artist
import ir.heydarii.musicmanager.repository.network.RetrofitMainInterface
import ir.heydarii.musicmanager.utils.Constants.Companion.NETWORK_PAGE_SIZE
import okio.IOException
import retrofit2.HttpException

private const val STARTING_PAGE = 1

class SearchArtistPagingSource(
    private val repository: RetrofitMainInterface,
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
            val artists = response.artistResults
            val nextKey =
                if (artists.itemsPerPage.toInt() * position < artists.totalResults.toInt())
                    position + (params.loadSize / NETWORK_PAGE_SIZE)
                else
                    null
            LoadResult.Page(
                data = artists.artistmatches.artist,
                prevKey = if (position == STARTING_PAGE) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}
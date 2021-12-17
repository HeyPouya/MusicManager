package ir.heydarii.musicmanager.features.topalbums

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ir.heydarii.musicmanager.pojos.topalbums.Album
import ir.heydarii.musicmanager.repository.network.RetrofitAlbumsInterface
import ir.heydarii.musicmanager.utils.NETWORK_PAGE_SIZE
import okio.IOException
import retrofit2.HttpException

private const val STARTING_PAGE = 1

class TopAlbumsPagingSource(
    private val repository: RetrofitAlbumsInterface,
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
            val response = repository.getTopAlbumsByArtist(artistName, position)
            val albums = response.topalbums.album
            val page = response.topalbums.attr
            val nextKey = if (NETWORK_PAGE_SIZE * position < page.total.toInt())
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            else
                null
            LoadResult.Page(
                data = albums,
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
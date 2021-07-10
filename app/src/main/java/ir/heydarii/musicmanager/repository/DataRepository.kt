package ir.heydarii.musicmanager.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import ir.heydarii.musicmanager.features.searchartist.SearchArtistPagingSource
import ir.heydarii.musicmanager.features.topalbums.TopAlbumsPagingSource
import ir.heydarii.musicmanager.pojos.savedalbums.AlbumEntity
import ir.heydarii.musicmanager.pojos.savedalbums.AlbumTracks
import ir.heydarii.musicmanager.pojos.savedalbums.TrackEntity
import ir.heydarii.musicmanager.pojos.searchartist.Artist
import ir.heydarii.musicmanager.pojos.topalbums.Album
import ir.heydarii.musicmanager.repository.local.AlbumsDAO
import ir.heydarii.musicmanager.repository.network.RetrofitMainInterface
import ir.heydarii.musicmanager.utils.Constants.Companion.NETWORK_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * All Observables are gathered here together
 * Network and Database
 */
class DataRepository @Inject constructor(
    private val network: RetrofitMainInterface,
    private val database: AlbumsDAO
) : RetrofitMainInterface by network,
    AlbumsDAO by database {

    suspend fun getAlbumDetails(
        artistName: String,
        albumName: String,
        offline: Boolean
    ): AlbumTracks {

        if (offline)
            return database.getSpecificAlbum(artistName, albumName)
        else {

            val albumDetails = network.getAlbumDetails(artistName, albumName)

            val image = albumDetails.album.image.last()?.text ?: ""
            val album = AlbumEntity(
                albumDetails.album.mbid,
                albumDetails.album.name,
                albumDetails.album.artist,
                image
            )
            val tracks = arrayListOf<TrackEntity>()
            albumDetails.album.tracks?.track?.forEach {
                tracks.add(TrackEntity(null, it.name, albumDetails.album.mbid))
            }

            return AlbumTracks(album, tracks)
        }
    }


    fun searchArtist(artistName: String): Flow<PagingData<Artist>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { SearchArtistPagingSource(network, artistName) }
    ).flow

    fun findTopAlbums(artistName: String): Flow<PagingData<Album>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { TopAlbumsPagingSource(network, artistName) }
    ).flow

    suspend fun saveAlbum(albumEntity: AlbumTracks) {
        database.saveAlbum(albumEntity.album)
        database.saveTracks(*albumEntity.tracks.toTypedArray())
    }
}

package ir.heydarii.musicmanager.framework

import com.pouyaheydari.android.core.data.ArtistDataSource
import com.pouyaheydari.android.core.domain.*
import ir.heydarii.musicmanager.framework.network.RetrofitAlbumsInterface
import javax.inject.Inject

class ArtistDataSource @Inject constructor(private val artistRetrofit: RetrofitAlbumsInterface) :
    ArtistDataSource {

    override suspend fun findArtist(artist: String, pageNumber: Int): ArtistResponse {
        val response = artistRetrofit.findArtist(artist, pageNumber)
        val artists = response.artistResults.artistmatches.artist.map {
            Artist(
                it.image.last().text,
                it.name
            )
        }
        return ArtistResponse(artists, response.artistResults.totalResults.toInt())
    }

    override suspend fun getTopAlbumsByArtist(artist: String, pageNumber: Int): AlbumResponse {
        val response = artistRetrofit.getTopAlbumsByArtist(artist, pageNumber)
        val albums = response.topalbums.album.map {
            Album(
                it.mbid.toString(),
                it.name,
                it.artist.name,
                it.image.last().text
            )
        }
        return AlbumResponse(albums, response.topalbums.attr.total.toInt())
    }

    override suspend fun getAlbumDetails(artist: String, albumName: String): AlbumDetails {
        val response = artistRetrofit.getAlbumDetails(artist, albumName)
        return AlbumDetails(
            response.album.artist,
            response.album.image.last()?.text.toString(),
            response.album.mbid,
            response.album.name,
            response.album.tracks?.track?.map { Track(null, it.name, response.album.mbid) }
                .orEmpty()
        )
    }
}
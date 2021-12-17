package ir.heydarii.musicmanager.pojos.albumdetails

import com.squareup.moshi.JsonClass
import ir.heydarii.musicmanager.pojos.searchartist.Image

/**
 * Response of album details
 */
data class AlbumDetailsResponseModel(val album: AlbumDetails)

data class AlbumDetails(
    val artist: String,
    val image: List<Image?>,
    val mbid: String,
    val name: String,
    val tracks: Tracks?,
    val wiki: Wiki?
)

data class Wiki(
    val content: String,
    val published: String,
    val summary: String
)

data class Tracks(val track: List<Track>)

data class Track(
    val duration: String,
    val name: String,
    val url: String
)
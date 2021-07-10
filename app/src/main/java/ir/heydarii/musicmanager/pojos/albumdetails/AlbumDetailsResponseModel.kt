package ir.heydarii.musicmanager.pojos.albumdetails

import com.squareup.moshi.JsonClass
import ir.heydarii.musicmanager.pojos.searchartist.Image

/**
 * Response of album details
 */
@JsonClass(generateAdapter = true)
data class AlbumDetailsResponseModel(val album: AlbumDetails)

@JsonClass(generateAdapter = true)
data class AlbumDetails(
    val artist: String,
    val image: List<Image?>,
    val mbid: String,
    val name: String,
    val tracks: Tracks?,
    val wiki: Wiki?
)

@JsonClass(generateAdapter = true)
data class Wiki(
    val content: String,
    val published: String,
    val summary: String
)

@JsonClass(generateAdapter = true)
data class Tracks(val track: List<Track>)

@JsonClass(generateAdapter = true)
data class Track(
    val duration: String,
    val name: String,
    val url: String
)
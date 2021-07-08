package ir.heydarii.musicmanager.pojos

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Response of album details
 */
@JsonClass(generateAdapter = true)
data class AlbumDetailsResponseModel(
    val album: AlbumDetails
)

@JsonClass(generateAdapter = true)
data class AlbumDetails(
    val artist: String,
    val image: List<Image?>,
    val listeners: String,
    val mbid: String,
    val name: String,
    val playcount: String,
    val tags: Tags,
    val tracks: Tracks,
    val url: String,
    val wiki: Wiki
)

@JsonClass(generateAdapter = true)
data class Wiki(
    val content: String,
    val published: String,
    val summary: String
)

@JsonClass(generateAdapter = true)
data class Tracks(
    val track: List<Track>
)

@JsonClass(generateAdapter = true)
data class Track(
    @Json(name = "@attr")
    val attr: AlbumDetailsAttr,
    val artist: AlbumDetailsArtist,
    val duration: String,
    val name: String,
    val streamable: Streamable,
    val url: String
)

@JsonClass(generateAdapter = true)
data class Streamable(
    @Json(name = "#text")
    val text: String,
    val fulltrack: String
)

@JsonClass(generateAdapter = true)
data class AlbumDetailsArtist(
    val mbid: String,
    val name: String,
    val url: String
)

@JsonClass(generateAdapter = true)
data class AlbumDetailsAttr(
    val rank: String
)

@JsonClass(generateAdapter = true)
data class Tags(
    val tag: List<Tag>
)

@JsonClass(generateAdapter = true)
data class Tag(
    val name: String,
    val url: String
)

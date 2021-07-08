package ir.heydarii.musicmanager.pojos

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Response of artist's tops albums
 */

@JsonClass(generateAdapter = true)
data class ArtistTopAlbumsResponseModel(
    val topalbums: Topalbums
)

@JsonClass(generateAdapter = true)
data class Topalbums(
    @Json(name = "@attr")
    val attr: Attr,
    val album: List<Album>
)

@JsonClass(generateAdapter = true)
data class Album(
    val artist: TopAlbumArtist,
    val image: List<Image>,
    val mbid: String?,
    val name: String,
    val playcount: Int,
    val url: String
)

@JsonClass(generateAdapter = true)
data class TopAlbumArtist(
    val mbid: String?,
    val name: String,
    val url: String
)

@JsonClass(generateAdapter = true)
data class Attr(
    val artist: String,
    val page: String,
    val perPage: String,
    val total: String,
    val totalPages: String
)

package ir.heydarii.musicmanager.pojos.topalbums

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ir.heydarii.musicmanager.pojos.searchartist.Image

/**
 * Response of artist's tops albums
 */

@JsonClass(generateAdapter = true)
data class ArtistTopAlbumsResponseModel(val topalbums: Topalbums)

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
)

@JsonClass(generateAdapter = true)
data class TopAlbumArtist(val name: String)

@JsonClass(generateAdapter = true)
data class Attr(val total: String)

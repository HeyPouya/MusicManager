package ir.heydarii.musicmanager.framework.network

import com.squareup.moshi.Json

/**
 * Response of artist's tops albums
 */

data class ArtistTopAlbumsResponseModel(val topalbums: Topalbums)

data class Topalbums(
    @field:Json(name = "@attr")
    val attr: Attr,
    val album: List<Album>
)

data class Album(
    val artist: TopAlbumArtist,
    val image: List<Image>,
    val mbid: String?,
    val name: String,
)

data class TopAlbumArtist(val name: String)

data class Attr(val total: String)

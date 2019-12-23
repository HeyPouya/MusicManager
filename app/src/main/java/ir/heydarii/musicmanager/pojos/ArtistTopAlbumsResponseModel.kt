package ir.heydarii.musicmanager.pojos

import com.google.gson.annotations.SerializedName

/**
 * Response of artist's tops albums
 */
data class ArtistTopAlbumsResponseModel(
        val topalbums: Topalbums
)

data class Topalbums(
        @SerializedName("@attr")
        val attr: Attr,
        val album: List<Album>
)

data class Album(
        val artist: TopAlbumArtist,
        val image: List<Image>,
        val mbid: String,
        val name: String,
        val playcount: Int,
        val url: String
)

data class TopAlbumArtist(
        val mbid: String,
        val name: String,
        val url: String
)

data class Attr(
        val artist: String,
        val page: String,
        val perPage: String,
        val total: String,
        val totalPages: String
)
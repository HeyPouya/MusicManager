package ir.heydarii.musicmanager.pojos

import com.google.gson.annotations.SerializedName

/**
 *
 * Response of album details
 */
data class AlbumDetailsResponseModel(
    val album: AlbumDetails
)

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


data class Wiki(
    val content: String,
    val published: String,
    val summary: String
)

data class Tracks(
    val track: List<Track>
)

data class Track(
    @SerializedName("@attr")
    val attr: AlbumDetailsAttr,
    val artist: AlbumDetailsArtist,
    val duration: String,
    val name: String,
    val streamable: Streamable,
    val url: String
)

data class Streamable(
    @SerializedName("#text")
    val text: String,
    val fulltrack: String
)

data class AlbumDetailsArtist(
    val mbid: String,
    val name: String,
    val url: String
)

data class AlbumDetailsAttr(
    val rank: String
)

data class Tags(
    val tag: List<Tag>
)

data class Tag(
    val name: String,
    val url: String
)
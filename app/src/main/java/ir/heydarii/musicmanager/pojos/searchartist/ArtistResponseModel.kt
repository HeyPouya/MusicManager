package ir.heydarii.musicmanager.pojos.searchartist

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Response of artist search
 */

@JsonClass(generateAdapter = true)
data class ArtistResponseModel(
    @Json(name = "results")
    val artistResults: ArtistResults
)

@JsonClass(generateAdapter = true)
data class ArtistResults(
    val artistmatches: Artistmatches,
    @Json(name = "opensearch:itemsPerPage")
    val itemsPerPage: String,
    @Json(name = "opensearch:totalResults")
    val totalResults: String
)


@JsonClass(generateAdapter = true)
data class Artistmatches(val artist: List<Artist>)

@JsonClass(generateAdapter = true)
data class Artist(
    val image: List<Image>,
    val name: String,
)

@JsonClass(generateAdapter = true)
data class Image(
    @Json(name = "#text")
    val text: String,
    val size: String
)

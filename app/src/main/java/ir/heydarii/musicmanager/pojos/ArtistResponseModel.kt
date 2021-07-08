package ir.heydarii.musicmanager.pojos

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Response of artist search
 */

@JsonClass(generateAdapter = true)
data class ArtistResponseModel(
    val results: Results
)

@JsonClass(generateAdapter = true)
data class Results(
    val artistmatches: Artistmatches,
    @Json(name = "opensearch:Query")
    val Query: OpensearchQuery,
    @Json(name = "opensearch:itemsPerPage")
    val itemsPerPage: String,
    @Json(name = "opensearch:startIndex")
    val startIndex: String,
    @Json(name = "opensearch:totalResults")
    val totalResults: String
)

@JsonClass(generateAdapter = true)
data class OpensearchQuery(
    @Json(name = "#text")
    val text: String,
    val role: String,
    val searchTerms: String,
    val startPage: String
)

@JsonClass(generateAdapter = true)
data class Artistmatches(
    val artist: List<Artist>
)

@JsonClass(generateAdapter = true)
data class Artist(
    val image: List<Image>,
    val listeners: String,
    val mbid: String,
    val name: String,
    val streamable: String,
    val url: String
)

@JsonClass(generateAdapter = true)
data class Image(
    @Json(name = "#text")
    val text: String,
    val size: String
)

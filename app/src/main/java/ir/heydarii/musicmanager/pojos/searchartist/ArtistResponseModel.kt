package ir.heydarii.musicmanager.pojos.searchartist

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Response of artist search
 */

data class ArtistResponseModel(
    @field:Json(name = "results")
    val artistResults: ArtistResults
)

data class ArtistResults(
    val artistmatches: Artistmatches,
    @field:Json(name = "opensearch:itemsPerPage")
    val itemsPerPage: String,
    @field:Json(name = "opensearch:totalResults")
    val totalResults: String
)


data class Artistmatches(val artist: List<Artist>)

data class Artist(
    val image: List<Image>,
    val name: String,
)

data class Image(
    @field:Json(name = "#text")
    val text: String,
    val size: String
)

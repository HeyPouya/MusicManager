package ir.heydarii.musicmanager.pojos

import com.google.gson.annotations.SerializedName

/*
Response of artist search
 */
data class ArtistResponseModel(
        val results: Results
)

data class Results(
        val artistmatches: Artistmatches,
        @SerializedName("opensearch:Query")
        val Query: OpensearchQuery,
        @SerializedName("opensearch:itemsPerPage")
        val itemsPerPage: String,
        @SerializedName("opensearch:startIndex")
        val startIndex: String,
        @SerializedName("opensearch:totalResults")
        val totalResults: String
)


data class OpensearchQuery(
        @SerializedName("#text")
        val text: String,
        val role: String,
        val searchTerms: String,
        val startPage: String
)

data class Artistmatches(
        val artist: List<Artist>
)

data class Artist(
        val image: List<Image>,
        val listeners: String,
        val mbid: String,
        val name: String,
        val streamable: String,
        val url: String
)

data class Image(
        @SerializedName("#text")
        val text: String,
        val size: String
)
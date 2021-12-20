package com.pouyaheydari.android.core.domain

data class ArtistResponse(val artists: List<Artist>, val totalResult: Int)
data class Artist(val image: String, val name: String)
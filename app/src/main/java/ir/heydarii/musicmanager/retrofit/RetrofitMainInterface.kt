package ir.heydarii.musicmanager.retrofit

import io.reactivex.Observable
import ir.heydarii.musicmanager.pojos.ArtistResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitMainInterface {

    @GET("?method=artist.search&artist={artist}&api_key={apiKey}&page={pageNumber}&format=json")
    fun findArtist(@Path("artist") artist: String, @Path("apiKey") apiKey: String, @Path("pageNumber") pageNumber: Int): Observable<ArtistResponseModel>
}
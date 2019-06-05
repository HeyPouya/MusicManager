package ir.heydarii.musicmanager.retrofit

import io.reactivex.Observable
import ir.heydarii.musicmanager.pojos.ArtistResponseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitMainInterface {

    @GET("?method=artist.search&format=json")
    fun findArtist(@Query("artist") artist: String, @Query("api_key") apiKey: String, @Query("page") pageNumber: Int): Observable<ArtistResponseModel>
}
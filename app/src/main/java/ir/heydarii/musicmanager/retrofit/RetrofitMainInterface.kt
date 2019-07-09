package ir.heydarii.musicmanager.retrofit

import io.reactivex.Observable
import io.reactivex.Single
import ir.heydarii.musicmanager.pojos.AlbumDetailsResponseModel
import ir.heydarii.musicmanager.pojos.ArtistResponseModel
import ir.heydarii.musicmanager.pojos.ArtistTopAlbumsResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitMainInterface {

    @GET("?method=artist.search&format=json")
    fun findArtist(@Query("artist") artist: String, @Query("api_key") apiKey: String, @Query("page") pageNumber: Int): Single<ArtistResponseModel>


    @GET("?method=artist.gettopalbums&format=json")
    fun getTopAlbumsByArtist(@Query("artist") artist: String, @Query("api_key") apiKey: String, @Query("page") pageNumber: Int): Single<ArtistTopAlbumsResponseModel>


    @GET("?method=album.getinfo&format=json")
    fun getAlbumDetails(@Query("artist") artist: String, @Query("api_key") apiKey: String, @Query("album") albumName: String): Single<AlbumDetailsResponseModel>
}
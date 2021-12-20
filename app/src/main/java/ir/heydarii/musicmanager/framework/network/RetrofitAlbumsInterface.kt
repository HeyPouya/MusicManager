package ir.heydarii.musicmanager.framework.network

import ir.heydarii.musicmanager.pojos.topalbums.ArtistTopAlbumsResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "2986239b2937225288a473b68a770f58"

/**
 * Retrofit interface to call the needed apis
 */
interface RetrofitAlbumsInterface {

    @GET("?method=artist.search&format=json")
    suspend fun findArtist(
        @Query("artist") artist: String,
        @Query("page") pageNumber: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): ArtistResponseModel

    @GET("?method=artist.gettopalbums&format=json")
    suspend fun getTopAlbumsByArtist(
        @Query("artist") artist: String,
        @Query("page") pageNumber: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): ArtistTopAlbumsResponseModel

    @GET("?method=album.getinfo&format=json")
    suspend fun getAlbumDetails(
        @Query("artist") artist: String,
        @Query("album") albumName: String,
        @Query("api_key") apiKey: String = API_KEY
    ): AlbumDetailsResponseModel
}

package ir.heydarii.musicmanager.repository.networkinteractor

import io.reactivex.Single
import ir.heydarii.musicmanager.pojos.AlbumDetailsResponseModel
import ir.heydarii.musicmanager.pojos.ArtistResponseModel
import ir.heydarii.musicmanager.pojos.ArtistTopAlbumsResponseModel
import ir.heydarii.musicmanager.retrofit.RetrofitMainInterface
import javax.inject.Inject

/*
 * All the network Observables are here
 */
class NetworkInteractor @Inject constructor(private val retrofitInterface: RetrofitMainInterface) {


    fun getArtistsName(artistName: String, page: Int, apiKey: String): Single<ArtistResponseModel> {
        return retrofitInterface.findArtist(artistName, apiKey, page)
    }


    fun getTopAlbumsByArtist(artistName: String, page: Int, apiKey: String): Single<ArtistTopAlbumsResponseModel> {
        return retrofitInterface.getTopAlbumsByArtist(artistName, apiKey, page)
    }

    fun getAlbumDetails(artistName: String, albumName: String, apiKey: String): Single<AlbumDetailsResponseModel> {
        return retrofitInterface.getAlbumDetails(artistName, apiKey, albumName)

    }
}
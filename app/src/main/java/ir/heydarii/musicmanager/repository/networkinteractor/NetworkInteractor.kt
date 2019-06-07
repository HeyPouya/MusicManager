package ir.heydarii.musicmanager.repository.networkinteractor

import io.reactivex.Observable
import ir.heydarii.musicmanager.pojos.ArtistResponseModel
import ir.heydarii.musicmanager.pojos.ArtistTopAlbumsResponseModel
import ir.heydarii.musicmanager.retrofit.RetrofitMainInterface
import javax.inject.Inject


class NetworkInteractor @Inject constructor(val retrofitInterface: RetrofitMainInterface) {


    fun getArtistsName(artistName: String, page: Int, apiKey: String): Observable<ArtistResponseModel> {
        return retrofitInterface.findArtist(artistName, apiKey, page)
    }


    fun getTopAlbumsByArtist(artistName: String, page: Int, apiKey: String): Observable<ArtistTopAlbumsResponseModel> {
        return retrofitInterface.getTopAlbumsByArtist(artistName, apiKey, page)
    }
}
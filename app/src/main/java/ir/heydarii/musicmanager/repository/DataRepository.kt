package ir.heydarii.musicmanager.repository

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ir.heydarii.musicmanager.base.BaseApplication
import ir.heydarii.musicmanager.pojos.AlbumDetailsResponseModel
import ir.heydarii.musicmanager.pojos.ArtistResponseModel
import ir.heydarii.musicmanager.pojos.ArtistTopAlbumsResponseModel
import ir.heydarii.musicmanager.repository.di.DaggerDataProviderComponent
import ir.heydarii.musicmanager.repository.networkinteractor.NetworkInteractor
import javax.inject.Inject

/*
 * All Observables are gathered here together
 * Network and Database
 */
class DataRepository @Inject constructor() {


    init {
        DaggerDataProviderComponent.builder().retrofitComponent(BaseApplication.getRetrofitComponent()).build()
            .inject(this)
    }

    @Inject
    lateinit var network: NetworkInteractor

    
    fun getArtistName(artistName: String, page: Int, apiKey: String): Observable<ArtistResponseModel> {
        return network.getArtistsName(artistName, page, apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getTopAlbumsByArtist(artistName: String, page: Int, apiKey: String): Observable<ArtistTopAlbumsResponseModel> {
        return network.getTopAlbumsByArtist(artistName, page, apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun getAlbumDetails(artistName: String, albumName: String, apiKey: String, offline: Boolean
    ): Observable<AlbumDetailsResponseModel> {

//        if (offline)
//            return AlbumDetailsResponseModel()
//        else
            return network.getAlbumDetails(artistName, albumName, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
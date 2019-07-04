package ir.heydarii.musicmanager.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ir.heydarii.musicmanager.base.BaseApplication
import ir.heydarii.musicmanager.pojos.AlbumDatabaseEntity
import ir.heydarii.musicmanager.pojos.ArtistResponseModel
import ir.heydarii.musicmanager.pojos.ArtistTopAlbumsResponseModel
import ir.heydarii.musicmanager.repository.dbinteractor.AlbumsDAO
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

    @Inject
    lateinit var database: AlbumsDAO


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


    fun getAlbumDetails(
        artistName: String, albumName: String, apiKey: String, offline: Boolean
    ): Observable<AlbumDatabaseEntity> {

        if (offline)
            return database.getSpecificAlbum(artistName, albumName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        else
            return network.getAlbumDetails(artistName, albumName, apiKey)
                .map {
                    val tracks = arrayListOf<String>()
                    it.album.tracks.track.forEach {
                        tracks.add(it.name)
                    }

                    val image = it.album.image.last().text ?: ""
                    AlbumDatabaseEntity(null, it.album.name, it.album.artist, image, tracks)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAllSavedAlbums(): Observable<List<AlbumDatabaseEntity>> {
        return database.getAllAlbums()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    fun saveAlbum(albumDatabaseEntity: AlbumDatabaseEntity): Completable {
        return database.saveAlbum(albumDatabaseEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }
}
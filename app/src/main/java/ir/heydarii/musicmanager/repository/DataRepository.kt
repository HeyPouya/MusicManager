package ir.heydarii.musicmanager.repository

import io.reactivex.Completable
import io.reactivex.Single
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

/**
 * All Observables are gathered here together
 * Network and Database
 */
class DataRepository @Inject constructor() {

    private var network: NetworkInteractor
    private var database: AlbumsDAO


    init {
        val component = DaggerDataProviderComponent.builder()
            .dataProvidersComponent(BaseApplication.getDataProviderComponent()).build()

        network = component.getNetworkInteractor()
        database = component.getDbInteractor()
    }


    fun getArtistName(artistName: String, page: Int, apiKey: String): Single<ArtistResponseModel> {
        return network.getArtistsName(artistName, page, apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getTopAlbumsByArtist(
        artistName: String,
        page: Int,
        apiKey: String
    ): Single<ArtistTopAlbumsResponseModel> {
        return network.getTopAlbumsByArtist(artistName, page, apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun getAlbumDetails(
        artistName: String,
        albumName: String,
        apiKey: String,
        offline: Boolean
    ): Single<AlbumDatabaseEntity> {

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

    fun getAllSavedAlbums(): Single<List<AlbumDatabaseEntity>> {
        return database.getAllAlbums()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun saveAlbum(albumDatabaseEntity: AlbumDatabaseEntity): Completable {
        return database.saveAlbum(albumDatabaseEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun doesAlbumExists(artistName: String, albumName: String): Single<Boolean> {
        return database.doesAlbumExists(artistName, albumName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun removeAlbum(artistName: String, albumName: String): Completable {
        return database.removeAlbum(artistName, albumName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
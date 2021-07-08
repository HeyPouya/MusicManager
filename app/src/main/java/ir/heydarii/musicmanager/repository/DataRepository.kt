package ir.heydarii.musicmanager.repository

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ir.heydarii.musicmanager.pojos.*
import ir.heydarii.musicmanager.repository.dbinteractor.AlbumsDAO
import ir.heydarii.musicmanager.repository.networkinteractor.NetworkRepository
import javax.inject.Inject

/**
 * All Observables are gathered here together
 * Network and Database
 */
class DataRepository @Inject constructor(val network: NetworkRepository, val database: AlbumsDAO) {

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
    ): Single<AlbumTracks> {

        if (offline)
            return database.getSpecificAlbum(artistName, albumName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        else
            return network.getAlbumDetails(artistName, albumName, apiKey)
                .map { albumDetails ->

                    val image = albumDetails.album.image.last()?.text ?: ""
                    val album = AlbumEntity(
                        albumDetails.album.mbid,
                        albumDetails.album.name,
                        albumDetails.album.artist,
                        image
                    )
                    val tracks = arrayListOf<TrackEntity>()
                    albumDetails.album.tracks.track.forEach {
                        tracks.add(TrackEntity(null, it.name, albumDetails.album.mbid))
                    }

                    AlbumTracks(album, tracks)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAllSavedAlbums(): Single<List<AlbumTracks>> {
        return database.getAllAlbums()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun saveAlbum(albumEntity: AlbumTracks): Completable {
        return database.saveAlbum(albumEntity.album)
            .andThen(database.saveTracks(*albumEntity.tracks.toTypedArray()))
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

package ir.heydarii.musicmanager.repository.dbinteractor

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import ir.heydarii.musicmanager.pojos.AlbumEntity
import ir.heydarii.musicmanager.pojos.AlbumTracks
import ir.heydarii.musicmanager.pojos.TrackEntity

/**
 * All Room queries are in this class
 */
@Dao
interface AlbumsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAlbum(albumEntity: AlbumEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTracks(vararg tracks: TrackEntity): Completable

    @Transaction
    @Query("SELECT * FROM albums")
    fun getAllAlbums(): Single<List<AlbumTracks>>

    @Transaction
    @Query("SELECT * FROM albums WHERE artistName = :artistName and albumName = :albumName LIMIT 1")
    fun getSpecificAlbum(artistName: String, albumName: String): Single<AlbumTracks>

    @Query("SELECT COUNT(*)>0 from albums WHERE artistName =:artistName and albumName =:albumName")
    fun doesAlbumExists(artistName: String, albumName: String): Single<Boolean>

    @Query("DELETE from albums where artistName = :artistName and albumName =:albumName")
    fun removeAlbum(artistName: String, albumName: String): Completable
}

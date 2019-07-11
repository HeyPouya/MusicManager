package ir.heydarii.musicmanager.repository.dbinteractor

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single
import ir.heydarii.musicmanager.pojos.AlbumDatabaseEntity

@Dao
interface AlbumsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAlbum(albumDatabaseEntity: AlbumDatabaseEntity): Completable

    @Query("SELECT * FROM albums")
    fun getAllAlbums(): Single<List<AlbumDatabaseEntity>>


    @Query("SELECT * FROM albums WHERE artist_name = :artistName and album_name = :albumName LIMIT 1")
    fun getSpecificAlbum(artistName: String, albumName: String): Single<AlbumDatabaseEntity>

    @Query("SELECT COUNT(*)>0 from albums WHERE artist_name =:artistName and album_name =:albumName")
    fun doesAlbumExists(artistName: String, albumName: String): Single<Boolean>

    @Query("DELETE from albums where artist_name = :artistName and album_name =:albumName")
    fun removeAlbum(artistName: String, albumName: String): Completable
}
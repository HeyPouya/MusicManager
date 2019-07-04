package ir.heydarii.musicmanager.repository.dbinteractor

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ir.heydarii.musicmanager.pojos.AlbumDatabaseEntity

@Dao
interface AlbumsDAO {

    @Insert
    fun saveAlbum(albumDatabaseEntity: AlbumDatabaseEntity): Completable

    @Query("SELECT * FROM albums")
    fun getAllAlbums(): Observable<List<AlbumDatabaseEntity>>


    @Query("SELECT * FROM albums WHERE artist_name = :artistName and album_name = :albumName LIMIT 1")
    fun getSpecificAlbum(artistName: String, albumName: String): Observable<AlbumDatabaseEntity>

}
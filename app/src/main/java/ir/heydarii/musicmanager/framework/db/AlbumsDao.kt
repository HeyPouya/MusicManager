package ir.heydarii.musicmanager.framework.db

import androidx.room.*
import ir.heydarii.musicmanager.framework.db.AlbumEntity
import ir.heydarii.musicmanager.framework.db.AlbumTracksEntity
import ir.heydarii.musicmanager.framework.db.TrackEntity

/**
 * All Room queries are in this class
 */
@Dao
interface AlbumsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAlbum(albumEntity: AlbumEntity)

    @Query("SELECT * FROM albums")
    suspend fun getAllAlbums(): List<AlbumEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTracks(vararg tracks: TrackEntity)

    @Query("SELECT * FROM tracks")
    suspend fun getAllTracks(): List<TrackEntity>

    @Transaction
    @Query("SELECT * FROM albums WHERE artistName = :artistName and albumName = :albumName LIMIT 1")
    suspend fun getSpecificAlbum(artistName: String, albumName: String): AlbumTracksEntity

    @Query("SELECT COUNT(*)>0 from albums WHERE artistName =:artistName and albumName =:albumName")
    suspend fun doesAlbumExists(artistName: String, albumName: String): Boolean

    @Query("DELETE from albums where artistName = :artistName and albumName =:albumName")
    suspend fun removeAlbum(artistName: String, albumName: String)
}

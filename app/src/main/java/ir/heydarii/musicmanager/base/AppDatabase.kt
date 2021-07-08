package ir.heydarii.musicmanager.base

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.heydarii.musicmanager.pojos.AlbumEntity
import ir.heydarii.musicmanager.pojos.TrackEntity
import ir.heydarii.musicmanager.repository.dbinteractor.AlbumsDAO

/**
 * Provides Room database
 */
@Database(entities = [AlbumEntity::class, TrackEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    /**
     * provides DAO for the Room database
     */
    abstract fun albumsDAO(): AlbumsDAO
}

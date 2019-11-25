package ir.heydarii.musicmanager.utils

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.heydarii.musicmanager.pojos.AlbumDatabaseEntity
import ir.heydarii.musicmanager.repository.dbinteractor.AlbumsDAO

/**
 * Provides Room database
 */
@Database(entities = [AlbumDatabaseEntity::class], version = 1, exportSchema = false)
@TypeConverters(StringListDataConverter::class)
abstract class AppDatabase : RoomDatabase() {

    /**
     * provides DAO for the Room database
     */
    abstract fun albumsDAO(): AlbumsDAO
}
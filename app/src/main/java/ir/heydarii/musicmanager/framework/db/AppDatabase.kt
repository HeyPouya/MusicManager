package ir.heydarii.musicmanager.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Provides access to [RoomDatabase]
 */
@Database(entities = [AlbumEntity::class, TrackEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Room handles this function
     * @return An instance of [AlbumsDao]
     */
    abstract fun albumsDAO(): AlbumsDao
}

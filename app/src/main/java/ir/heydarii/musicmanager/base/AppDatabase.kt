package ir.heydarii.musicmanager.base

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.heydarii.musicmanager.pojos.savedalbums.AlbumEntity
import ir.heydarii.musicmanager.pojos.savedalbums.TrackEntity
import ir.heydarii.musicmanager.repository.local.AlbumsDao

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

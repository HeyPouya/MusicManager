package ir.heydarii.musicmanager.base

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.heydarii.musicmanager.pojos.AlbumEntity
import ir.heydarii.musicmanager.pojos.TrackEntity
import ir.heydarii.musicmanager.repository.local.AlbumsDAO

/**
 * Provides access to [RoomDatabase]
 */
@Database(entities = [AlbumEntity::class, TrackEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Room handles this function
     * @return An instance of [AlbumsDAO]
     */
    abstract fun albumsDAO(): AlbumsDAO
}

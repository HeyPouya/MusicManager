package ir.heydarii.musicmanager.framework.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * We save the albums data via this object in the database
 */
@Entity(tableName = "albums")
data class AlbumEntity(
    // Unfortunately the only unique id that we can get from last.fm api is mbid String so we have to use it as our primary key
    @PrimaryKey
    val id: String,
    val albumName: String,
    val artistName: String,
    var image: String,
)

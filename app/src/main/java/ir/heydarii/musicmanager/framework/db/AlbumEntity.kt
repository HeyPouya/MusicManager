package ir.heydarii.musicmanager.framework.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * We save the albums data via this object in the database
 */
@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val albumName: String,
    val artistName: String,
    var image: String,
)

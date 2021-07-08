package ir.heydarii.musicmanager.pojos

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * We save the albums data via this object in the database
 */
@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey
    val id: String,
    val albumName: String,
    val artistName: String,
    var image: String,
)

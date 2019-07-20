package ir.heydarii.musicmanager.pojos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
We save the albums data via this object in the database
 */
@Entity(tableName = "albums")
data class AlbumDatabaseEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    @ColumnInfo(name = "album_name") var albumName: String,
    @ColumnInfo(name = "artist_name") var artistName: String,
    @ColumnInfo(name = "image") var image: String,
    @ColumnInfo(name = "tracks") var tracks: List<String> = listOf()
)
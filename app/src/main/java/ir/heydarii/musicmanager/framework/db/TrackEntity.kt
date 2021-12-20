package ir.heydarii.musicmanager.framework.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = AlbumEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("albumId"),
        onDelete = ForeignKey.CASCADE
    )],
    tableName = "tracks"
)
data class TrackEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String,
    @ColumnInfo(index = true)
    val albumId: String
)

package ir.heydarii.musicmanager.framework.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class AlbumTracksEntity(
    @Embedded
    val album: AlbumEntity,
    @Relation(parentColumn = "id", entityColumn = "albumId")
    val tracks: List<TrackEntity>
)

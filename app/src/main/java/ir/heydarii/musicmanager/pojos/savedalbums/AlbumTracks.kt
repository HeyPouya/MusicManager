package ir.heydarii.musicmanager.pojos.savedalbums

import androidx.room.Embedded
import androidx.room.Relation

data class AlbumTracks(
    @Embedded
    val album: AlbumEntity,
    @Relation(parentColumn = "id", entityColumn = "albumId")
    val tracks: List<TrackEntity>
)

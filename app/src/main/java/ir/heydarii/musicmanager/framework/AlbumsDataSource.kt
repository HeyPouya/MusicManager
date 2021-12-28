package ir.heydarii.musicmanager.framework

import com.pouyaheydari.android.core.data.AlbumsDataSource
import com.pouyaheydari.android.core.domain.Album
import com.pouyaheydari.android.core.domain.AlbumDetails
import com.pouyaheydari.android.core.domain.AlbumResponse
import com.pouyaheydari.android.core.domain.Track
import ir.heydarii.musicmanager.framework.db.AlbumEntity
import ir.heydarii.musicmanager.framework.db.AlbumsDao
import ir.heydarii.musicmanager.framework.db.TrackEntity
import javax.inject.Inject

class AlbumsDataSource @Inject constructor(private val albumsDao: AlbumsDao) : AlbumsDataSource {
    override suspend fun saveAlbum(album: Album):Long = with(album) {
        albumsDao.saveAlbum(AlbumEntity(id, albumName, artistName, image))
    }

    override suspend fun getAllAlbums(): AlbumResponse {
        val albums =
            albumsDao.getAllAlbums().map { with(it) { Album(id, albumName, artistName, image) } }
        return AlbumResponse(albums, albums.size)
    }

    override suspend fun saveTracks(vararg tracks: Track) {
        val trackEntities = tracks.map { with(it) { TrackEntity(id, name, albumId) } }
        albumsDao.saveTracks(*trackEntities.toTypedArray())
    }

    override suspend fun getAllTracks(): List<Track> =
        albumsDao.getAllTracks().map { with(it) { Track(id, name, albumId) } }


    override suspend fun getSpecificAlbum(artistName: String, albumName: String): AlbumDetails {
        val albumTracks = albumsDao.getSpecificAlbum(artistName, albumName)
        return AlbumDetails(
            albumTracks.album.artistName,
            albumTracks.album.image,
            albumTracks.album.albumName,
            albumTracks.tracks.map { Track(it.id, it.name, it.albumId) }
        )
    }

    override suspend fun doesAlbumExists(artistName: String, albumName: String): Boolean =
        albumsDao.doesAlbumExists(artistName, albumName)


    override suspend fun removeAlbum(artistName: String, albumName: String) =
        albumsDao.removeAlbum(artistName, albumName)
}
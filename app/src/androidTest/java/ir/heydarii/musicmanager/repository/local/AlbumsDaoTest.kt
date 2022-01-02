package ir.heydarii.musicmanager.repository.local

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import ir.heydarii.musicmanager.framework.db.AppDatabase
import ir.heydarii.musicmanager.framework.db.AlbumEntity
import ir.heydarii.musicmanager.framework.db.AlbumTracksEntity
import ir.heydarii.musicmanager.framework.db.AlbumsDao
import ir.heydarii.musicmanager.framework.db.TrackEntity
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumsDaoTest : TestCase() {

    private lateinit var db: AppDatabase
    private lateinit var albumsDao: AlbumsDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        albumsDao = db.albumsDAO()
    }

    @Test
    fun test_write_and_read_one_album() = runBlocking {
        val album = getAlbumList().first()

        albumsDao.saveAlbum(album)

        val result = albumsDao.getAllAlbums().first()
        assertEquals(album, result)
    }

    @Test
    fun test_write_and_read_list_of_albums() = runBlocking {
        val albums = getAlbumList()

        albums.forEach { albumsDao.saveAlbum(it) }

        val result = albumsDao.getAllAlbums()
        assertEquals(albums, result)
    }

    @Test
    fun test_replacing_one_album() = runBlocking {
        val album = getAlbumList().first()
        val conflictedAlbum = album.copy(albumName = "NEW ALBUM NAME")

        albumsDao.saveAlbum(album)
        albumsDao.saveAlbum(conflictedAlbum)

        val result = albumsDao.getAllAlbums()
        assertEquals(1, result.size)
        assertEquals(conflictedAlbum, result.first())
    }

    @Test
    fun test_get_specific_album_that_exists_with_empty_tracks() = runBlocking {
        val albums = getAlbumList()

        albums.forEach { albumsDao.saveAlbum(it) }

        val selectedAlbum = albums[1]
        val result = albumsDao.getSpecificAlbum(selectedAlbum.artistName, selectedAlbum.albumName)
        val selectedAlbumTrack = AlbumTracksEntity(selectedAlbum, listOf())
        assertEquals(selectedAlbumTrack, result)
    }

    @Test
    fun test_get_specific_album_that_doesnt_exists_with_empty_tracks() = runBlocking {
        val firstAlbum = getAlbumList().first()
        albumsDao.saveAlbum(firstAlbum)

        val secondAlbum = getAlbumList()[1]
        val result = albumsDao.getSpecificAlbum(secondAlbum.artistName, secondAlbum.albumName)
        assertEquals(null, result)
    }

    @Test
    fun test_get_remove_album() = runBlocking {
        val firstAlbum = getAlbumList().first()

        albumsDao.saveAlbum(firstAlbum)
        albumsDao.removeAlbum(firstAlbum.artistName, firstAlbum.albumName)

        val result = albumsDao.doesAlbumExists(firstAlbum.artistName, firstAlbum.albumName)
        assertFalse(result)
    }

    @Test
    fun test_get_specific_album_with_tracks() = runBlocking {
        val albums = getAlbumList()
        val tracks = getTrackList()

        albums.forEach { albumsDao.saveAlbum(it) }
        albumsDao.saveTracks(*tracks.toTypedArray())

        val selectedAlbum = albums[1]
        val selectedTrack = tracks[1]
        val result = albumsDao.getSpecificAlbum(selectedAlbum.artistName, selectedAlbum.albumName)
        val selectedAlbumTrack = AlbumTracksEntity(selectedAlbum, listOf(selectedTrack))
        assertEquals(selectedAlbumTrack, result)
    }

    @Test(expected = SQLiteConstraintException::class)
    fun test_saving_tracks_without_saving_albums() = runBlocking {
        val tracks = getTrackList()

        albumsDao.saveTracks(*tracks.toTypedArray())
    }

    @Test
    fun test_write_and_read_tracks() = runBlocking {
        val albums = getAlbumList()
        val tracks = getTrackList()

        albums.forEach { albumsDao.saveAlbum(it) }
        albumsDao.saveTracks(*tracks.toTypedArray())

        val result = albumsDao.getAllTracks()
        assertEquals(tracks, result)
    }

    @Test
    fun test_check_if_an_album_exists_true() = runBlocking {
        val album = getAlbumList().first()
        val artistName = album.artistName
        val albumName = album.albumName

        albumsDao.saveAlbum(album)

        val result = albumsDao.doesAlbumExists(artistName, albumName)
        assertTrue(result)
    }

    @Test
    fun test_check_if_an_album_exists_false() = runBlocking {
        val firstAlbum = getAlbumList().first()
        val secondAlbum = getAlbumList()[1]
        val artistName = secondAlbum.artistName
        val albumName = secondAlbum.albumName

        albumsDao.saveAlbum(firstAlbum)

        val result = albumsDao.doesAlbumExists(artistName, albumName)
        assertFalse(result)
    }

    @After
    public override fun tearDown() {
        db.close()
    }

    private fun getAlbumList() = listOf(
        AlbumEntity(
            1,
            "TEST ALBUM NAME1",
            "TEST ARTIST NAME1",
            "https://testimage1.com"
        ),
        AlbumEntity(
            2,
            "TEST ALBUM NAME2",
            "TEST ARTIST NAME2",
            "https://testimage2.com"
        ),
        AlbumEntity(
            3,
            "TEST ALBUM NAME3",
            "TEST ARTIST NAME3",
            "https://testimage3.com"
        ),
    )

    private fun getTrackList() = listOf(
        TrackEntity(
            1,
            "TEST TRACK NAME1",
            1,
        ),
        TrackEntity(
            2,
            "TEST TRACK NAME2",
            2,
        ),
        TrackEntity(
            3,
            "TEST TRACK NAME3",
            3,
        ),
    )
}
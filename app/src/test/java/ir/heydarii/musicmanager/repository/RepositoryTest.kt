package ir.heydarii.musicmanager.repository

import ir.heydarii.musicmanager.framework.db.AlbumTracksEntity
import ir.heydarii.musicmanager.framework.db.AlbumsDao
import ir.heydarii.musicmanager.framework.network.RetrofitAlbumsInterface
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class RepositoryTest {

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var network: RetrofitAlbumsInterface

    @Mock
    private lateinit var local: AlbumsDao

    @InjectMocks
    private lateinit var repository: Repository

    @Test
    fun test_save_album() = runBlocking {
        val album = Mockito.mock(AlbumTracksEntity::class.java)

        repository.saveAlbum(album)

        Mockito.verify(local).saveAlbum(album.album)
        Mockito.verify(local).saveTracks(*album.tracks.toTypedArray())
    }
}
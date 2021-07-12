package ir.heydarii.musicmanager.repository

import ir.heydarii.musicmanager.pojos.savedalbums.AlbumTracks
import ir.heydarii.musicmanager.repository.local.AlbumsDao
import ir.heydarii.musicmanager.repository.network.RetrofitAlbumsInterface
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
        val album = Mockito.mock(AlbumTracks::class.java)

        repository.saveAlbum(album)

        Mockito.verify(local).saveAlbum(album.album)
        Mockito.verify(local).saveTracks(*album.tracks.toTypedArray())
    }
}
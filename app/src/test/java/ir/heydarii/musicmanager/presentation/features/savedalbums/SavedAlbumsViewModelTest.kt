package ir.heydarii.musicmanager.presentation.features.savedalbums

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pouyaheydari.android.core.domain.AlbumResponse
import com.pouyaheydari.android.core.interctors.GetAllAlbums
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import ir.heydarii.musicmanager.presentation.CoroutineDispatcherRule
import ir.heydarii.musicmanager.presentation.albumListGenerator
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*

class SavedAlbumsViewModelTest {

    private lateinit var viewModel: SavedAlbumsViewModel

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule = CoroutineDispatcherRule()

    @MockK
    private lateinit var getAllAlbums: GetAllAlbums

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = SavedAlbumsViewModel(getAllAlbums, dispatcherRule.testDispatcher)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test calling get all albums then return all albums stored in database`() =
        dispatcherRule.runBlockingTest {

            val albumResponse = AlbumResponse(albumListGenerator(), 6)
            viewModel.getAlbumsLiveData().observeForever { }
            coEvery { getAllAlbums() }.coAnswers { albumResponse }

            viewModel.getAllAlbums()

            Assert.assertEquals(albumResponse.albums, viewModel.getAlbumsLiveData().value?.data)
        }
}
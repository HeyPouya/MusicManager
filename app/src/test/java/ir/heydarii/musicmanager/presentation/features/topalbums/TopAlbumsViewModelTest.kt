package ir.heydarii.musicmanager.presentation.features.topalbums

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.pouyaheydari.android.core.interctors.GetTopAlbumsByArtist
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import ir.heydarii.musicmanager.presentation.CoroutineDispatcherRule
import ir.heydarii.musicmanager.presentation.albumListGenerator
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*

class TopAlbumsViewModelTest {

    private lateinit var viewModel: TopAlbumsViewModel

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule = CoroutineDispatcherRule()

    @MockK
    private lateinit var getTopAlbums: GetTopAlbumsByArtist

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = TopAlbumsViewModel(getTopAlbums)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `if the artist name is same then the responded flow must be exactly the same`() =
        runBlockingTest {
            val artistName = "SAMPLE ARTIST"
            val flow = flowOf(PagingData.from(albumListGenerator()))
            coEvery { getTopAlbums(artistName) }.coAnswers { flow }

            val firstResponse = viewModel.requestTopAlbums(artistName)
            val secondResponse = viewModel.requestTopAlbums(artistName)

            Assert.assertEquals(firstResponse, secondResponse)
        }
}
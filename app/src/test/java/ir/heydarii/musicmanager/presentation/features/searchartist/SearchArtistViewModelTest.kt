package ir.heydarii.musicmanager.presentation.features.searchartist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.pouyaheydari.android.core.domain.Artist
import com.pouyaheydari.android.core.interctors.FindArtist
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import ir.heydarii.musicmanager.presentation.CoroutineDispatcherRule
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*

class SearchArtistViewModelTest {

    private lateinit var viewModel: SearchArtistViewModel

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule = CoroutineDispatcherRule()

    @MockK
    private lateinit var findArtist: FindArtist

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = SearchArtistViewModel(findArtist)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `if the artist name is same then the responded flow must be exactly the same`() =
        runBlockingTest {
            val artistName = "SAMPLE ARTIST"
            val flow = flowOf(PagingData.from(artistGenerator()))
            coEvery { findArtist(artistName) }.coAnswers { flow }

            val firstResponse = viewModel.onUserSearchedArtist(artistName)
            val secondResponse = viewModel.onUserSearchedArtist(artistName)

            Assert.assertEquals(firstResponse, secondResponse)
        }

    private fun artistGenerator() = listOf(
        Artist("", "ARTIST 1"),
        Artist("", "ARTIST 2"),
        Artist("", "ARTIST 3"),
        Artist("", "ARTIST 4"),
        Artist("", "ARTIST 5"),
    )
}
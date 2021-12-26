package ir.heydarii.musicmanager.presentation.features.searchartist

import androidx.paging.PagingSource.LoadParams.Refresh
import androidx.paging.PagingSource.LoadResult.Error
import androidx.paging.PagingSource.LoadResult.Page
import ir.heydarii.musicmanager.framework.network.Artist
import ir.heydarii.musicmanager.framework.network.ArtistResponseModel
import ir.heydarii.musicmanager.framework.network.ArtistResults
import ir.heydarii.musicmanager.framework.network.Artistmatches
import ir.heydarii.musicmanager.framework.network.RetrofitAlbumsInterface
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

//class SearchArtistPagingSourceTest {
//
//    @get:Rule
//    var mockitoRule: MockitoRule = MockitoJUnit.rule()
//
//    @Mock
//    private lateinit var network: RetrofitAlbumsInterface
//
//    @Test
//    @ExperimentalCoroutinesApi
//    fun test_load_returns_error_when_list_is_empty() = runBlockingTest {
//        val pagingSource = SearchArtistPagingSource(network, "TEST ARTIST")
//
//        val result = pagingSource.load(
//            Refresh(
//                key = null,
//                loadSize = 2,
//                placeholdersEnabled = false
//            )
//        )
//
//        Assert.assertTrue(result is Error)
//    }
//
//    @Test
//    @ExperimentalCoroutinesApi
//    fun test_load_success_with_one_page() = runBlockingTest {
//        val pagingSource = SearchArtistPagingSource(network, "TEST ARTIST")
//        val artistModel =
//            ArtistResponseModel(ArtistResults(Artistmatches(getResponse()), "30", "30"))
//        Mockito.`when`(network.findArtist("TEST ARTIST", 1)).thenReturn(artistModel)
//        val expected = Page(data = getResponse(), prevKey = null, nextKey = null)
//
//
//        val result = pagingSource.load(
//            Refresh(
//                key = null,
//                loadSize = 1,
//                placeholdersEnabled = false
//            )
//        )
//
//        Assert.assertEquals(expected, result)
//    }
//
//    @Test
//    @ExperimentalCoroutinesApi
//    fun test_load_success_with_two_pages() = runBlockingTest {
//        val pagingSource = SearchArtistPagingSource(network, "TEST ARTIST")
//        val artistModel =
//            ArtistResponseModel(ArtistResults(Artistmatches(getResponse()), "30", "60"))
//        Mockito.`when`(network.findArtist("TEST ARTIST", 1)).thenReturn(artistModel)
//        val expected = Page(data = getResponse(), prevKey = null, nextKey = 1)
//
//
//        val result = pagingSource.load(
//            Refresh(
//                key = null,
//                loadSize = 1,
//                placeholdersEnabled = false
//            )
//        )
//
//        Assert.assertEquals(expected, result)
//    }
//
//    private fun getResponse() = listOf(
//        Artist(listOf(), "TEST ARTIST1"),
//        Artist(listOf(), "TEST ARTIST2"),
//        Artist(listOf(), "TEST ARTIST3"),
//        Artist(listOf(), "TEST ARTIST4"),
//        Artist(listOf(), "TEST ARTIST5"),
//        Artist(listOf(), "TEST ARTIST6"),
//    )
//}


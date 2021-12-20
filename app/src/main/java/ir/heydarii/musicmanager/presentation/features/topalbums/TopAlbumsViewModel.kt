package ir.heydarii.musicmanager.presentation.features.topalbums

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pouyaheydari.android.core.domain.Album
import com.pouyaheydari.android.core.interctors.GetTopAlbumsByArtist
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.heydarii.musicmanager.framework.BaseViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * ViewModel of [TopAlbumsFragment]
 */
@HiltViewModel
class TopAlbumsViewModel @Inject constructor(private val getTopAlbumsByArtist: GetTopAlbumsByArtist) :
    BaseViewModel() {

    private var currentSearchResult: Flow<PagingData<Album>>? = null
    private var currentArtistName: String? = null

    /**
     * Fetches the top albums of a selected artist
     *
     * @param artistName name of the artist of the albums
     */
    fun requestTopAlbums(artistName: String): Flow<PagingData<Album>> {
        val lastResult = currentSearchResult
        if (artistName == currentArtistName && lastResult != null) {
            return lastResult
        }

        currentArtistName = artistName
        val newResult = getTopAlbumsByArtist(artistName).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}

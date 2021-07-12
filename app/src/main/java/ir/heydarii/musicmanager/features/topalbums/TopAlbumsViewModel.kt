package ir.heydarii.musicmanager.features.topalbums

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.heydarii.musicmanager.base.BaseViewModel
import ir.heydarii.musicmanager.pojos.topalbums.Album
import ir.heydarii.musicmanager.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * ViewModel of [TopAlbumsFragment]
 */
@HiltViewModel
class TopAlbumsViewModel @Inject constructor(private val repository: Repository) :
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
        val newResult = repository.findTopAlbums(artistName).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}

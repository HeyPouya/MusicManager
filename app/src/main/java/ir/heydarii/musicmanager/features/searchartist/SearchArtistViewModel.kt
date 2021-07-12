package ir.heydarii.musicmanager.features.searchartist

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.heydarii.musicmanager.base.BaseViewModel
import ir.heydarii.musicmanager.pojos.searchartist.Artist
import ir.heydarii.musicmanager.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * ViewModel for the search artist view
 */
@HiltViewModel
class SearchArtistViewModel @Inject constructor(private val repository: Repository) :
    BaseViewModel() {

    private var currentSearchResult: Flow<PagingData<Artist>>? = null
    private var currentArtistName: String? = null

    /**
     * Fetches all artists with the name that user enters
     *
     * @param artistName name of the artists
     * @return flow of artists names is used by paging
     */
    fun onUserSearchedArtist(artistName: String): Flow<PagingData<Artist>> {
        val lastResult = currentSearchResult
        if (artistName == currentArtistName && lastResult != null) {
            return lastResult
        }
        currentArtistName = artistName
        val newResult = repository.searchArtist(artistName).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}

package ir.heydarii.musicmanager.features.searchartist

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.heydarii.musicmanager.base.BaseViewModel
import ir.heydarii.musicmanager.pojos.Artist
import ir.heydarii.musicmanager.repository.DataRepository
import ir.heydarii.musicmanager.utils.ViewNotifierEnums
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * ViewModel for the search artist view
 */
@HiltViewModel
class SearchArtistViewModel @Inject constructor(private val repository: DataRepository) :
    BaseViewModel() {

    private var currentSearchResult: Flow<PagingData<Artist>>? = null
    private var currentArtistName: String? = null

    /**
     * Fetches all artists with the name that user enters
     */
    fun onUserSearchedArtist(artistName: String): Flow<PagingData<Artist>> {
        val lastResult = currentSearchResult
        if (artistName == currentArtistName && lastResult != null) {
            return lastResult
        }

        currentArtistName = artistName
        viewNotifier.value = ViewNotifierEnums.SHOW_LOADING
        val newResult = repository.searchArtist(artistName).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}

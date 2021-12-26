package ir.heydarii.musicmanager.presentation.features.searchartist

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pouyaheydari.android.core.domain.Artist
import com.pouyaheydari.android.core.interctors.FindArtist
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.heydarii.musicmanager.presentation.BaseViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * ViewModel for the search artist view
 */
@HiltViewModel
class SearchArtistViewModel @Inject constructor(private val findArtist: FindArtist) :
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
        val newResult = findArtist(artistName).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}

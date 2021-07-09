package ir.heydarii.musicmanager.features.topalbums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.heydarii.musicmanager.base.BaseViewModel
import ir.heydarii.musicmanager.pojos.Album
import ir.heydarii.musicmanager.repository.DataRepository
import ir.heydarii.musicmanager.utils.ViewNotifierEnums
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for TopAlbums of an Artist view
 */
@HiltViewModel
class TopAlbumsViewModel @Inject constructor(private val dataRepository: DataRepository) :
    BaseViewModel() {

    private val topAlbumsData = MutableLiveData<List<Album>>()
    private var page = 1
    private val list = ArrayList<Album>()
    private var shouldLoadMore = true
    private var artist = ""

    /**
     * Serving LiveData instead of MutableLiveData for activity
     */
    fun getTopAlbumsLiveData(): LiveData<List<Album>> = topAlbumsData

    /**
     * Fetches the top albums of a selected artist
     */
    fun onTopAlbumsRequested(artistName: String = artist, apiKey: String) {

        if (artistName.isNotEmpty())
            artist = artistName

        if (shouldLoadMore) {
            viewNotifier.value = ViewNotifierEnums.SHOW_LOADING

            viewModelScope.launch {
                val topAlbums = dataRepository.getTopAlbumsByArtist(artistName, page, apiKey)
                if (topAlbums.topalbums.album.isEmpty())
                    shouldLoadMore = false

                page++
                list.addAll(topAlbums.topalbums.album)
                viewNotifier.value = ViewNotifierEnums.HIDE_LOADING
                topAlbumsData.value = list

            }
//                        {
//                        viewNotifier.value = ViewNotifierEnums.HIDE_LOADING
//                        viewNotifier.value = ViewNotifierEnums.ERROR_GETTING_DATA
//                    })
        }
    }
}

package ir.heydarii.musicmanager.features.savedalbums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.heydarii.musicmanager.base.BaseViewModel
import ir.heydarii.musicmanager.pojos.AlbumTracks
import ir.heydarii.musicmanager.repository.DataRepository
import ir.heydarii.musicmanager.utils.ViewNotifierEnums
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for SavedAlbums view
 */
@HiltViewModel
class SavedAlbumsViewModel @Inject constructor(private val repository: DataRepository) :
    BaseViewModel() {

    private val albumsList = MutableLiveData<List<AlbumTracks>>()

    /**
     * Fetches all albums from database
     */
    fun getAllAlbums() {
        viewModelScope.launch {
            val albums = repository.getAllAlbums()
            if (albums.isEmpty())
                viewNotifier.value = ViewNotifierEnums.EMPTY_STATE
            else
                albumsList.postValue(albums)

        }
//                {
//                    viewNotifier.value = ViewNotifierEnums.ERROR_GETTING_DATA
//                })
    }

    /**
     * returns an immutable instance of live data
     */
    fun getAlbumList(): LiveData<List<AlbumTracks>> {
        return albumsList
    }
}

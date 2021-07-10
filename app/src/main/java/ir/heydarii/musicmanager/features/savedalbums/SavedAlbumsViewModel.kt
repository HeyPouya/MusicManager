package ir.heydarii.musicmanager.features.savedalbums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.heydarii.musicmanager.base.BaseViewModel
import ir.heydarii.musicmanager.pojos.savedalbums.AlbumEntity
import ir.heydarii.musicmanager.pojos.savedalbums.SavedAlbumsState
import ir.heydarii.musicmanager.pojos.savedalbums.SavedAlbumsState.Loading
import ir.heydarii.musicmanager.repository.DataRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel of [SavedAlbumsFragment]
 */
@HiltViewModel
class SavedAlbumsViewModel @Inject constructor(private val repository: DataRepository) :
    BaseViewModel() {

    private val albumsLiveData = MutableLiveData<SavedAlbumsState<List<AlbumEntity>>>()

    /**
     * @return [LiveData] instance of [albumsLiveData]
     */
    fun getAlbumsLiveData(): LiveData<SavedAlbumsState<List<AlbumEntity>>> = albumsLiveData

    /**
     * Fetches all saved albums from database
     */
    fun getAllAlbums() {
        viewModelScope.launch {
            albumsLiveData.postValue(Loading())
            val albums = repository.getAllAlbums()
            albumsLiveData.postValue(SavedAlbumsState.Success(albums))
        }
    }
}

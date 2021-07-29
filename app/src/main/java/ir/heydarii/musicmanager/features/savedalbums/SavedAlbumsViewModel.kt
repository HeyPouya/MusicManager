package ir.heydarii.musicmanager.features.savedalbums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.heydarii.musicmanager.base.BaseViewModel
import ir.heydarii.musicmanager.pojos.savedalbums.AlbumEntity
import ir.heydarii.musicmanager.pojos.savedalbums.SavedAlbumsViewState
import ir.heydarii.musicmanager.pojos.savedalbums.SavedAlbumsViewState.*
import ir.heydarii.musicmanager.repository.Repository
import javax.inject.Inject

/**
 * ViewModel of [SavedAlbumsFragment]
 */
@HiltViewModel
class SavedAlbumsViewModel @Inject constructor(private val repository: Repository) :
    BaseViewModel() {

    private val albumsLiveData = MutableLiveData<SavedAlbumsViewState<List<AlbumEntity>>>()

    /**
     * @return [LiveData] instance of [albumsLiveData]
     */
    fun getAlbumsLiveData(): LiveData<SavedAlbumsViewState<List<AlbumEntity>>> = albumsLiveData

    /**
     * Fetches all saved albums from database
     */
    fun getAllAlbums() = launch {
        albumsLiveData.postValue(Loading())
        val albums = repository.getAllAlbums()
        val response = if (albums.isEmpty()) EmptyList() else Success(albums)
        albumsLiveData.postValue(response)
    }
}

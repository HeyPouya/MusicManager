package ir.heydarii.musicmanager.presentation.features.savedalbums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pouyaheydari.android.core.domain.Album
import com.pouyaheydari.android.core.interctors.GetAllAlbums
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.heydarii.musicmanager.framework.BaseViewModel
import ir.heydarii.musicmanager.presentation.features.savedalbums.SavedAlbumsViewState.*
import javax.inject.Inject

/**
 * ViewModel of [SavedAlbumsFragment]
 */
@HiltViewModel
class SavedAlbumsViewModel @Inject constructor(private val getAllAlbumsUseCase: GetAllAlbums) :
    BaseViewModel() {

    private val albumsLiveData = MutableLiveData<SavedAlbumsViewState<List<Album>>>()

    /**
     * @return [LiveData] instance of [albumsLiveData]
     */
    fun getAlbumsLiveData(): LiveData<SavedAlbumsViewState<List<Album>>> = albumsLiveData

    /**
     * Fetches all saved albums from database
     */
    fun getAllAlbums() = launch {
        albumsLiveData.postValue(Loading())
        val albums = getAllAlbumsUseCase().albums
        val response = if (albums.isEmpty()) EmptyList() else Success(albums)
        albumsLiveData.postValue(response)
    }
}

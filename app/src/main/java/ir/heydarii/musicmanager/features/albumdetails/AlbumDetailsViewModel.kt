package ir.heydarii.musicmanager.features.albumdetails

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
 * ViewModel for AlbumDetails view
 */
@HiltViewModel
class AlbumDetailsViewModel @Inject constructor(private val dataRepository: DataRepository) :
    BaseViewModel() {

    private val albumDetailsResponse = MutableLiveData<AlbumTracks>()
    private var albumData: AlbumTracks? = null
    private val doesAlbumExistsInDb = MutableLiveData<Boolean>()
    private var isAlbumSaved = false

    /**
     * Gets the album data
     */
    fun getAlbum(artistName: String, albumName: String, apiKey: String, offline: Boolean) {

        viewNotifier.value = ViewNotifierEnums.SHOW_LOADING

        checkAlbumExistenceInDb(artistName, albumName)

        viewModelScope.launch {
            val album = dataRepository.getAlbumDetails(artistName, albumName, offline)

            if (album.tracks.isEmpty())
                viewNotifier.value = ViewNotifierEnums.EMPTY_STATE
            else
                viewNotifier.value = ViewNotifierEnums.NOT_EMPTY

            albumDetailsResponse.postValue(album)
            albumData = album
            viewNotifier.value = ViewNotifierEnums.HIDE_LOADING

        }
    }

//                {
//                    viewNotifier.value = ViewNotifierEnums.HIDE_LOADING
//                    viewNotifier.value = ViewNotifierEnums.ERROR_GETTING_DATA
//                })


    private fun saveAlbum(imagePath: String) {
        if (albumData != null) {
            albumData?.album?.image = imagePath
            viewModelScope.launch {
                dataRepository.saveAlbum(albumData!!)
                viewNotifier.value = ViewNotifierEnums.SAVED_INTO_DB
                isAlbumSaved = true
            }

//                {
//                    viewNotifier.value = ViewNotifierEnums.ERROR_SAVING_DATA
//                })
        } else
            viewNotifier.value = ViewNotifierEnums.ERROR_DATA_NOT_AVAILABLE
    }

    /**
     * Decides to call remove or save function
     */
    fun onClickedOnSaveButton(imagePath: String) {
        when (isAlbumSaved) {
            true -> removeAlbum()
            false -> saveAlbum(imagePath)
        }
    }

    private fun removeAlbum() {
        if (albumData != null)
            viewModelScope.launch {
                dataRepository.removeAlbum(
                    albumData!!.album.artistName,
                    albumData!!.album.albumName
                )
                viewNotifier.value = ViewNotifierEnums.REMOVED_FROM_DB
                isAlbumSaved = false

            }
//                 {
//                    viewNotifier.value = ViewNotifierEnums.ERROR_REMOVING_DATA
//                })
        else
            viewNotifier.value = ViewNotifierEnums.ERROR_DATA_NOT_AVAILABLE
    }

    /**
     * Returns an ImmutableLiveData instance of albumDetailsResponse
     */
    fun getAlbumsResponse(): LiveData<AlbumTracks> = albumDetailsResponse

    private fun checkAlbumExistenceInDb(artistName: String, albumName: String) {
        viewModelScope.launch {
            val response = dataRepository.doesAlbumExists(artistName, albumName)
            doesAlbumExistsInDb.postValue(response)
            isAlbumSaved = response

        }
//                {
//                checkAlbumExistenceInDb(artistName, albumName)
//            })
    }

    fun getAlbumExistenceResponse(): LiveData<Boolean> {
        return doesAlbumExistsInDb
    }
}

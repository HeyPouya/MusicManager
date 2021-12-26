package ir.heydarii.musicmanager.presentation.features.albumdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pouyaheydari.android.core.domain.AlbumDetails
import com.pouyaheydari.android.core.interctors.DoesAlbumExists
import com.pouyaheydari.android.core.interctors.GetAlbumDetails
import com.pouyaheydari.android.core.interctors.RemoveAlbum
import com.pouyaheydari.android.core.interctors.SaveAlbum
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.heydarii.musicmanager.presentation.BaseViewModel
import ir.heydarii.musicmanager.presentation.features.albumdetails.AlbumDetailsViewState.*
import ir.heydarii.musicmanager.presentation.utils.coroutinesExceptionHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for AlbumDetails view
 */
@HiltViewModel
class AlbumDetailsViewModel @Inject constructor(
    private val doesAlbumExists: DoesAlbumExists,
    private val saveAlbum: SaveAlbum,
    private val getAlbumDetails: GetAlbumDetails,
    private val removeAlbum: RemoveAlbum,
    private val dispatcher: CoroutineDispatcher
) :
    BaseViewModel() {

    private val albumDetailsLiveData = MutableLiveData<AlbumDetailsViewState<AlbumDetails>>()
    private var albumDataEntity: AlbumDetails? = null

    /**
     * @return [LiveData] version of [albumDetailsLiveData]
     */
    fun getAlbumsResponse(): LiveData<AlbumDetailsViewState<AlbumDetails>> =
        albumDetailsLiveData

    /**
     * Fetches album data from repository
     *
     * @param artistName name of the artist
     * @param albumName name of the album
     */
    fun getAlbum(artistName: String, albumName: String) =
        viewModelScope.launch(dispatcher + coroutinesExceptionHandler(errorLiveData)) {
            albumDetailsLiveData.postValue(Loading())
            val album = getAlbumDetails(artistName, albumName)
            albumDataEntity = album
            val response = if (album.tracks.isEmpty()) EmptyTrackList(album) else Success(album)
            albumDetailsLiveData.postValue(response)
        }

    fun checkBookMark(artistName: String, albumName: String) =
        viewModelScope.launch(dispatcher + coroutinesExceptionHandler(errorLiveData)) {
            val isSaved = checkBookmarkStatus(artistName, albumName)
            val bookMarkResponse: AlbumDetailsViewState<AlbumDetails> =
                if (isSaved) Saved() else NotSaved()
            launch(Dispatchers.Main) {
                albumDetailsLiveData.value = bookMarkResponse
            }
        }

    private fun saveAlbum() = albumDataEntity?.let {
        viewModelScope.launch(dispatcher + coroutinesExceptionHandler(errorLiveData)) {
            saveAlbum(it)
            albumDetailsLiveData.postValue(Saved())
        }
    }

    /**
     * Adds or removes the album from database, based on the current state
     */
    fun bookMarkClicked() =
        albumDataEntity?.let {
            viewModelScope.launch(dispatcher + coroutinesExceptionHandler(errorLiveData)) {
                when (checkBookmarkStatus(it.artist, it.name)) {
                    true -> removeAlbum()
                    false -> saveAlbum()
                }
            }
        }

    private fun removeAlbum() = albumDataEntity?.let {
        viewModelScope.launch(dispatcher + coroutinesExceptionHandler(errorLiveData)) {
            removeAlbum(it.artist, it.name)
            albumDetailsLiveData.postValue(NotSaved())
        }
    }

    private suspend fun checkBookmarkStatus(artistName: String, albumName: String) =
        doesAlbumExists(artistName, albumName)
}

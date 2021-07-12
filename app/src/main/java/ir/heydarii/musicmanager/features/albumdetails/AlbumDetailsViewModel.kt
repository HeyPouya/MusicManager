package ir.heydarii.musicmanager.features.albumdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.heydarii.musicmanager.base.BaseViewModel
import ir.heydarii.musicmanager.pojos.albumdetails.AlbumDetailsViewState
import ir.heydarii.musicmanager.pojos.albumdetails.AlbumDetailsViewState.*
import ir.heydarii.musicmanager.pojos.savedalbums.AlbumTracks
import ir.heydarii.musicmanager.repository.Repository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for AlbumDetails view
 */
@HiltViewModel
class AlbumDetailsViewModel @Inject constructor(private val repository: Repository) :
    BaseViewModel() {

    private val albumDetailsLiveData = MutableLiveData<AlbumDetailsViewState<AlbumTracks>>()
    private var albumData: AlbumTracks? = null

    /**
     * @return [LiveData] version of [albumDetailsLiveData]
     */
    fun getAlbumsResponse(): LiveData<AlbumDetailsViewState<AlbumTracks>> = albumDetailsLiveData

    /**
     * Fetches album data from repository
     *
     * @param artistName name of the artist
     * @param albumName name of the album
     * @param offline If user has navigated to this view via saved items view  or via search view
     */
    fun getAlbum(artistName: String, albumName: String, offline: Boolean) = viewModelScope.launch {
        albumDetailsLiveData.postValue(Loading())

        //Check Bookmark
        val isSaved = checkBookmarkStatus(artistName, albumName)
        val bookMarkResponse: AlbumDetailsViewState<AlbumTracks> =
            if (isSaved) Saved() else NotSaved()
        albumDetailsLiveData.postValue(bookMarkResponse)

        //Fetch data
        val album = repository.getAlbumDetails(artistName, albumName, offline) ?: return@launch
        albumData = album
        val response = if (album.tracks.isEmpty()) EmptyTrackList(album) else Success(album)
        albumDetailsLiveData.postValue(response)
    }

    private fun saveAlbum() = albumData?.let {
        viewModelScope.launch {
            repository.saveAlbum(it)
            albumDetailsLiveData.postValue(Saved())
        }
    }

    /**
     * Adds or removes the album from database, based on the current state
     */
    fun bookMarkClicked() =
        albumData?.let {
            viewModelScope.launch {
                when (checkBookmarkStatus(it.album.artistName, it.album.albumName)) {
                    true -> removeAlbum()
                    false -> saveAlbum()
                }
            }
        }

    private fun removeAlbum() = albumData?.let {
        viewModelScope.launch {
            repository.removeAlbum(it.album.artistName, it.album.albumName)
            albumDetailsLiveData.postValue(NotSaved())
        }
    }

    private suspend fun checkBookmarkStatus(artistName: String, albumName: String) =
        repository.doesAlbumExists(artistName, albumName)
}

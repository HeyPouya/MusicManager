package ir.heydarii.musicmanager.presentation.features.albumdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pouyaheydari.android.core.domain.AlbumDetails
import com.pouyaheydari.android.core.interctors.DoesAlbumExists
import com.pouyaheydari.android.core.interctors.GetAlbumDetails
import com.pouyaheydari.android.core.interctors.RemoveAlbum
import com.pouyaheydari.android.core.interctors.SaveAlbum
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.heydarii.musicmanager.framework.BaseViewModel
import ir.heydarii.musicmanager.presentation.features.albumdetails.AlbumDetailsViewState.*
import javax.inject.Inject

/**
 * ViewModel for AlbumDetails view
 */
@HiltViewModel
class AlbumDetailsViewModel @Inject constructor(
    private val doesAlbumExists: DoesAlbumExists,
    private val saveAlbum: SaveAlbum,
    private val getAlbumDetails: GetAlbumDetails,
    private val removeAlbum: RemoveAlbum
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
     * @param offline If user has navigated to this view via saved items view  or via search view
     */
    fun getAlbum(artistName: String, albumName: String, offline: Boolean) = launch {
        albumDetailsLiveData.postValue(Loading())

        //Check Bookmark
        val isSaved = checkBookmarkStatus(artistName, albumName)
        val bookMarkResponse: AlbumDetailsViewState<AlbumDetails> =
            if (isSaved) Saved() else NotSaved()
        albumDetailsLiveData.postValue(bookMarkResponse)

        //Fetch data
        val album = getAlbumDetails(artistName, albumName)
        albumDataEntity = album
        val response = if (album.tracks.isEmpty()) EmptyTrackList(album) else Success(album)
        albumDetailsLiveData.postValue(response)
    }

    private fun saveAlbum() = albumDataEntity?.let {
        launch {
            saveAlbum(it)
            albumDetailsLiveData.postValue(Saved())
        }
    }

    /**
     * Adds or removes the album from database, based on the current state
     */
    fun bookMarkClicked() =
        albumDataEntity?.let {
            launch {
                when (checkBookmarkStatus(it.artist, it.name)) {
                    true -> removeAlbum()
                    false -> saveAlbum()
                }
            }
        }

    private fun removeAlbum() = albumDataEntity?.let {
        launch {
            removeAlbum(it.artist, it.name)
            albumDetailsLiveData.postValue(NotSaved())
        }
    }

    private suspend fun checkBookmarkStatus(artistName: String, albumName: String) =
        doesAlbumExists(artistName, albumName)
}

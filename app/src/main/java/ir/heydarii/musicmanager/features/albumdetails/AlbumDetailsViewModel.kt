package ir.heydarii.musicmanager.features.albumdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import ir.heydarii.musicmanager.base.BaseViewModel
import ir.heydarii.musicmanager.pojos.AlbumDatabaseEntity
import ir.heydarii.musicmanager.repository.DataRepository
import ir.heydarii.musicmanager.utils.ViewNotifierEnums
import javax.inject.Inject

/**
 * ViewModel for AlbumDetails view
 */
class AlbumDetailsViewModel @Inject constructor(val dataRepository: DataRepository) : BaseViewModel() {

    private val composite = CompositeDisposable()
    private val albumDetailsResponse = MutableLiveData<AlbumDatabaseEntity>()
    private var albumData: AlbumDatabaseEntity? = null
    private val doesAlbumExistsInDb = MutableLiveData<Boolean>()
    private var isAlbumSaved = false


    /**
     * Gets the album data
     */
    fun getAlbum(artistName: String, albumName: String, apiKey: String, offline: Boolean) {

        viewNotifier.value = ViewNotifierEnums.SHOW_LOADING

        checkAlbumExistenceInDb(artistName, albumName)

        composite.add(
                dataRepository.getAlbumDetails(artistName, albumName, apiKey, offline)
                        .subscribe({

                            if (it.tracks.isEmpty())
                                viewNotifier.value = ViewNotifierEnums.EMPTY_STATE
                            else
                                viewNotifier.value = ViewNotifierEnums.NOT_EMPTY

                            albumDetailsResponse.value = it
                            albumData = it
                            viewNotifier.value = ViewNotifierEnums.HIDE_LOADING
                        }, {
                            viewNotifier.value = ViewNotifierEnums.HIDE_LOADING
                            viewNotifier.value = ViewNotifierEnums.ERROR_GETTING_DATA
                            Logger.d(it)
                        })
        )
    }

    private fun saveAlbum(imagePath: String) {
        if (albumData != null) {
            albumData?.image = imagePath
            composite.add(
                    dataRepository.saveAlbum(albumData!!)
                            .subscribe({
                                viewNotifier.value = ViewNotifierEnums.SAVED_INTO_DB
                                isAlbumSaved = true
                            }, {
                                Logger.d(it)
                                viewNotifier.value = ViewNotifierEnums.ERROR_SAVING_DATA
                            })
            )
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
            composite.add(
                    dataRepository.removeAlbum(albumData!!.artistName, albumData!!.albumName)
                            .subscribe({
                                viewNotifier.value = ViewNotifierEnums.REMOVED_FROM_DB
                                isAlbumSaved = false
                            }, {
                                Logger.d(it)
                                viewNotifier.value = ViewNotifierEnums.ERROR_REMOVING_DATA
                            })
            )
        else
            viewNotifier.value = ViewNotifierEnums.ERROR_DATA_NOT_AVAILABLE
    }

    /**
     * Returns an ImmutableLiveData instance of albumDetailsResponse
     */
    fun getAlbumsResponse(): LiveData<AlbumDatabaseEntity> = albumDetailsResponse

    private fun checkAlbumExistenceInDb(artistName: String, albumName: String) {
        composite.add(
                dataRepository.doesAlbumExists(artistName, albumName)
                        .subscribe({
                            doesAlbumExistsInDb.value = it
                            isAlbumSaved = it
                        }, {
                            Logger.d(it)
                            checkAlbumExistenceInDb(artistName, albumName)
                        })
        )
    }

    fun getAlbumExistenceResponse(): LiveData<Boolean> {
        return doesAlbumExistsInDb
    }

    /**
     * Clearing the RX disposables
     */
    override fun onCleared() {
        composite.dispose()
        super.onCleared()
    }

}
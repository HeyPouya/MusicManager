package ir.heydarii.musicmanager.features.albumdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import ir.heydarii.musicmanager.base.BaseViewModel
import ir.heydarii.musicmanager.base.di.DaggerDataRepositoryComponent
import ir.heydarii.musicmanager.pojos.AlbumDatabaseEntity
import ir.heydarii.musicmanager.repository.DataRepository
import ir.heydarii.musicmanager.utils.ViewNotifierEnums

class AlbumDetailsViewModel : BaseViewModel() {


    private val dataRepository: DataRepository = DaggerDataRepositoryComponent.create().getDataRepository()
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
                            albumDetailsResponse.value = it
                            albumData = it
                            viewNotifier.value = ViewNotifierEnums.HIDE_LOADING
                        }, {
                            viewNotifier.value = ViewNotifierEnums.HIDE_LOADING
                            //TODO : Error handling
                            Logger.d(it)
                        })
        )
    }

    /**
     * Saves an album into the database
     */
    private fun saveAlbum() {
        if (albumData != null)
            composite.add(dataRepository.saveAlbum(albumData!!)
                    .subscribe({
                        viewNotifier.value = ViewNotifierEnums.SAVED_INTO_DB
                        isAlbumSaved = true
                    }, {
                        Logger.d(it)
                        //TODO : Handle Error
                    }))
        //TODO : Set else to show error message
    }

    fun onClickedOnSaveButton() {
        when (isAlbumSaved) {
            true -> removeAlbum()
            false -> saveAlbum()
        }
    }

    private fun removeAlbum() {
        if (albumData != null)
            composite.add(dataRepository.removeAlbum(albumData!!.artistName, albumData!!.albumName)
                    .subscribe({
                        viewNotifier.value = ViewNotifierEnums.REMOVED_FROM_DB
                        isAlbumSaved = false
                    }, {
                        Logger.d(it)
                        //TODO : Handle Error
                    }))
        //TODO : Set else to show error message

    }

    /**
     * Returns an ImmutableLiveData instance of albumDetailsResponse
     */
    fun getAlbumsResponse(): LiveData<AlbumDatabaseEntity> = albumDetailsResponse


    private fun checkAlbumExistenceInDb(artistName: String, albumName: String) {
        composite.add(dataRepository.doestAlbumExists(artistName, albumName)
                .subscribe({
                    doesAlbumExistsInDb.value = it
                    isAlbumSaved = it
                }, {
                    Logger.d(it)
                    //TODO : Some error Handling
                }))
    }

    /**
     * Returns an ImmutableLiveData instance of doesAlbumExistsInDb
     */
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
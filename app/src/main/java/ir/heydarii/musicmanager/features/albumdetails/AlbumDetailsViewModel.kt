package ir.heydarii.musicmanager.features.albumdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import ir.heydarii.musicmanager.base.BaseViewModel
import ir.heydarii.musicmanager.pojos.AlbumDatabaseEntity
import ir.heydarii.musicmanager.repository.DataRepository
import ir.heydarii.musicmanager.utils.Consts
import ir.heydarii.musicmanager.utils.ViewNotifierEnums

class AlbumDetailsViewModel : BaseViewModel() {

    //TODO : get the repository from dagger

    private val repository = DataRepository()
    private val composite = CompositeDisposable()
    private val albumDetailsResponse = MutableLiveData<AlbumDatabaseEntity>()
    private var albumData: AlbumDatabaseEntity? = null


    /**
     * Gets the album data
     */
    fun getAlbum(artistName: String, albumName: String, apiKey: String, offline: Boolean) {

        viewNotifier.value = ViewNotifierEnums.SHOW_LOADING

        composite.add(
                repository.getAlbumDetails(artistName, albumName, apiKey, offline)
                        .subscribe({
                            viewNotifier.value = ViewNotifierEnums.HIDE_LOADING
                            albumDetailsResponse.value = it
                            albumData = it
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
    fun saveAlbum() {

        if (albumData != null)
            composite.add(repository.saveAlbum(albumData!!)
                    .subscribe({

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


    /**
     * Clearing the RX disposables
     */
    override fun onCleared() {
        composite.dispose()
        super.onCleared()
    }
}
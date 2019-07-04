package ir.heydarii.musicmanager.features.albumdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import ir.heydarii.musicmanager.base.BaseViewModel
import ir.heydarii.musicmanager.pojos.AlbumDatabaseEntity
import ir.heydarii.musicmanager.repository.DataRepository
import ir.heydarii.musicmanager.utils.Consts

class AlbumDetailsViewModel : BaseViewModel() {

    //TODO : get the repository from dagger

    private val repository = DataRepository()
    private val composite = CompositeDisposable()
    private val albumDetailsResponse = MutableLiveData<AlbumDatabaseEntity>()


    /**
     * Gets the album data
     */
    fun getAlbum(artistName: String, albumName: String, apiKey: String, offline: Boolean) {

        loadingData.value = Consts.SHOW_LOADING

        composite.add(
            repository.getAlbumDetails(artistName, albumName, apiKey, offline)
                .subscribe({
                    loadingData.value = Consts.HIDE_LOADING
                    albumDetailsResponse.value = it
                }, {
                    loadingData.value = Consts.HIDE_LOADING
                    //TODO : Error handling
                    Logger.d(it)
                })
        )

    }

    /**
     * Saves an album into the database
     */
    fun saveAlbum(data: AlbumDatabaseEntity) {
        composite.add(
            repository.saveAlbum(data)
                .subscribe({

                }, {
                    Logger.d(it)
                    //TODO : Handle Error
                })
        )
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
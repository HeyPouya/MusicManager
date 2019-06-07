package ir.heydarii.musicmanager.features.albumdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import ir.heydarii.musicmanager.base.BaseViewModel
import ir.heydarii.musicmanager.pojos.AlbumDetailsResponseModel
import ir.heydarii.musicmanager.repository.DataRepository
import ir.heydarii.musicmanager.utils.Consts

class AlbumDetailsViewModel : BaseViewModel() {

    //TODO : get the repository from dagger

    private val repository = DataRepository()
    private val composite = CompositeDisposable()
    private val albumDetailsResponse = MutableLiveData<AlbumDetailsResponseModel>()

    fun getAlbum(artistName: String, albumName: String, apiKey: String) {

        loadingData.value = Consts.SHOW_LOADING

        composite.add(repository.getAlbumDetails(artistName, albumName, apiKey)
                .subscribe({

                    loadingData.value = Consts.HIDE_LOADING
                    albumDetailsResponse.value = it
                }, {
                    loadingData.value = Consts.HIDE_LOADING
                    //TODO : Error handling
                    Logger.d(it)
                }))
    }

    fun getAlbumsResponse(): LiveData<AlbumDetailsResponseModel> = albumDetailsResponse


    override fun onCleared() {
        composite.dispose()
        super.onCleared()
    }
}
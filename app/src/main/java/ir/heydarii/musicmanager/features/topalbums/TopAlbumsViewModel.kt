package ir.heydarii.musicmanager.features.topalbums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import ir.heydarii.musicmanager.base.BaseViewModel
import ir.heydarii.musicmanager.pojos.ArtistTopAlbumsResponseModel
import ir.heydarii.musicmanager.repository.DataRepository
import ir.heydarii.musicmanager.utils.Consts

class TopAlbumsViewModel : BaseViewModel() {

    private val topAlbumsData = MutableLiveData<ArtistTopAlbumsResponseModel>()
    //TODO : Provide the repository with dagger
    private val dataRepository = DataRepository()
    private val composite = CompositeDisposable()


    /**
     * Fetches the top albums of a selected artist
     */
    fun onTopAlbumsRequested(artistName: String, page: Int, apiKey: String) {
        loadingData.value = Consts.SHOW_LOADING

        composite.add(dataRepository.getTopAlbumsByArtist(artistName, page, apiKey)
                .subscribe({
                    loadingData.value = Consts.HIDE_LOADING
                    topAlbumsData.value = it
                }, {

                    loadingData.value = Consts.HIDE_LOADING
                    //TODO : Error handling
                    Logger.d(it)
                }))
    }

    /**
     * Serving LiveData instead of MutableLiveData for activity
     */
    fun getTopAlbumsLiveData(): LiveData<ArtistTopAlbumsResponseModel> = topAlbumsData


    /**
     * Disposing all disposables after the ViewModel dies
     */
    override fun onCleared() {
        composite.dispose()
        super.onCleared()
    }
}
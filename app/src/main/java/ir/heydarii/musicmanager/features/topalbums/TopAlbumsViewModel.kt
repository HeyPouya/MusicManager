package ir.heydarii.musicmanager.features.topalbums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import ir.heydarii.musicmanager.base.BaseViewModel
import ir.heydarii.musicmanager.pojos.ArtistTopAlbumsResponseModel
import ir.heydarii.musicmanager.repository.DataRepository

class TopAlbumsViewModel : BaseViewModel() {

    private val topAlbumsData = MutableLiveData<ArtistTopAlbumsResponseModel>()
    //TODO : Provide the repository with dagger
    private val dataRepository = DataRepository()
    private val composite = CompositeDisposable()


    /**
     * Fetches the top albums of a selected artist
     */
    fun onTopAlbumsRequested(artistName: String, page: Int, apiKey: String) {
        composite.add(dataRepository.getTopAlbumsByArtist(artistName, page, apiKey)
                .subscribe({
                    topAlbumsData.value = it
                }, {

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
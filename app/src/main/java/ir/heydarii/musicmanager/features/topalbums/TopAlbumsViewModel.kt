package ir.heydarii.musicmanager.features.topalbums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import ir.heydarii.musicmanager.base.BaseViewModel
import ir.heydarii.musicmanager.base.di.DaggerDataRepositoryComponent
import ir.heydarii.musicmanager.pojos.ArtistTopAlbumsResponseModel
import ir.heydarii.musicmanager.repository.DataRepository
import ir.heydarii.musicmanager.utils.ViewNotifierEnums

class TopAlbumsViewModel : BaseViewModel() {

    private val dataRepository: DataRepository = DaggerDataRepositoryComponent.create().getDataRepository()
    private val topAlbumsData = MutableLiveData<ArtistTopAlbumsResponseModel>()
    private val composite = CompositeDisposable()

    /**
     * Fetches the top albums of a selected artist
     */
    fun onTopAlbumsRequested(artistName: String, page: Int, apiKey: String) {
        viewNotifier.value = ViewNotifierEnums.SHOW_LOADING

        composite.add(dataRepository.getTopAlbumsByArtist(artistName, page, apiKey)
                .subscribe({
                    viewNotifier.value = ViewNotifierEnums.HIDE_LOADING
                    topAlbumsData.value = it
                }, {

                    viewNotifier.value = ViewNotifierEnums.HIDE_LOADING
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
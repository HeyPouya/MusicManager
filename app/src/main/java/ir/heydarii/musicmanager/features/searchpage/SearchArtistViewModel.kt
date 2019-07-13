package ir.heydarii.musicmanager.features.searchpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import ir.heydarii.musicmanager.base.BaseViewModel
import ir.heydarii.musicmanager.base.di.DaggerDataRepositoryComponent
import ir.heydarii.musicmanager.pojos.ArtistResponseModel
import ir.heydarii.musicmanager.repository.DataRepository
import ir.heydarii.musicmanager.utils.ViewNotifierEnums

class SearchArtistViewModel : BaseViewModel() {

    private val dataRepository: DataRepository = DaggerDataRepositoryComponent.create().getDataRepository()
    private val composite = CompositeDisposable()
    private val artistResponse = MutableLiveData<ArtistResponseModel>()


    /**
     * Fetches all artists with the name that user enters
     */
    fun onUserSearchedArtist(artistName: String, page: Int, apiKey: String) {

        viewNotifier.value = ViewNotifierEnums.SHOW_LOADING

        //disposing all disposables before adding a new one
        if (composite.size() > 0)
            composite.clear()

        composite.add(dataRepository.getArtistName(artistName, page, apiKey)
                .subscribe({
                    artistResponse.value = it
                    viewNotifier.value = ViewNotifierEnums.HIDE_LOADING
                }, {

                    Logger.d(it.message)
                    viewNotifier.value = ViewNotifierEnums.HIDE_LOADING
                    viewNotifier.value = ViewNotifierEnums.ERROR_GETTING_DATA
                })
        )
    }

    fun getArtistResponse(): LiveData<ArtistResponseModel> = artistResponse

    /**
     * Disposing all disposables after the ViewModel dies
     */
    override fun onCleared() {
        composite.dispose()
        super.onCleared()
    }
}
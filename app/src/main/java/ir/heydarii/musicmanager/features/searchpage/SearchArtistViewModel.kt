package ir.heydarii.musicmanager.features.searchpage

import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import ir.heydarii.musicmanager.base.BaseViewModel
import ir.heydarii.musicmanager.pojos.ArtistResponseModel
import ir.heydarii.musicmanager.repository.DataRepository

class SearchArtistViewModel : BaseViewModel() {

    //TODO : Provide the repository with dagger
    private val repository = DataRepository()
    private val composite = CompositeDisposable()
    val artistResponse = MutableLiveData<ArtistResponseModel>()


    /**
     * Fetches all artists with the name that user enters
     */
    fun onUserSearchedArtist(artistName: String, page: Int, apiKey: String) {

        //disposing all disposables before adding a new one
        if (composite.size() > 0)
            composite.clear()

        composite.add(repository.getArtistName(artistName, page, apiKey)
                .subscribe({
                    artistResponse.value = it
                }, {

                    Logger.d(it.message)
                    //TODO : Add error handling
                }))

    }

    /**
     * Disposing all disposables after the ViewModel dies
     */
    override fun onCleared() {
        composite.dispose()
        super.onCleared()
    }

}

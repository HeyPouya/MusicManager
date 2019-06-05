package ir.heydarii.musicmanager.features.searchpage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import ir.heydarii.musicmanager.pojos.ArtistResponseModel
import ir.heydarii.musicmanager.repository.DataRepository
import ir.heydarii.musicmanager.utils.Consts

class SearchArtistViewModel : ViewModel() {

    val repository = DataRepository()
    private val composite = CompositeDisposable()
    val artistResponse = MutableLiveData<ArtistResponseModel>()


    fun onUserSearchedArtist(artistName: String) {
        if (composite.size() > 0)
            composite.clear()

        composite.add(repository.getArtistName(artistName, 1, Consts.API_KEY)
                .subscribe({
                    artistResponse.value = it
                }, {

                    Logger.d(it.message)
                    //TODO : Add error handling
                }))

    }

    override fun onCleared() {
        super.onCleared()
        composite.dispose()
    }

}

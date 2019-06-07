package ir.heydarii.musicmanager.features.topalbums

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import ir.heydarii.musicmanager.pojos.ArtistTopAlbumsResponseModel
import ir.heydarii.musicmanager.repository.DataRepository

class TopAlbumsViewModel : ViewModel() {

    val topAlbumsData = MutableLiveData<ArtistTopAlbumsResponseModel>()

    private val dataRepository = DataRepository()
    private val composite = CompositeDisposable()


    fun onTopAlbumsRequested(artistName: String, page: Int, apiKey: String) {

        composite.add(dataRepository.getTopAlbumsByArtist(artistName, page, apiKey)
                .subscribe({

                    topAlbumsData.value = it
                }, {

                    //TODO : Error handling
                    Logger.d(it)
                }))
    }


    override fun onCleared() {
        composite.dispose()
        super.onCleared()
    }
}
package ir.heydarii.musicmanager.features.searchartist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import ir.heydarii.musicmanager.base.BaseViewModel
import ir.heydarii.musicmanager.base.di.DaggerDataRepositoryComponent
import ir.heydarii.musicmanager.pojos.Artist
import ir.heydarii.musicmanager.repository.DataRepository
import ir.heydarii.musicmanager.utils.ViewNotifierEnums

class SearchArtistViewModel : BaseViewModel() {

    private val dataRepository: DataRepository = DaggerDataRepositoryComponent.create().getDataRepository()
    private val composite = CompositeDisposable()
    private val artistResponse = MutableLiveData<List<Artist>>()
    private var page = 1
    private var shouldLoadMore = true
    private var list = arrayListOf<Artist>()

    /**
     * Fetches all artists with the name that user enters
     */
    fun onUserSearchedArtist(artistName: String, apiKey: String, isLoadMore: Boolean) {

        prepareDataToSearch(isLoadMore)

        viewNotifier.value = ViewNotifierEnums.SHOW_LOADING

        //disposing all disposables before adding a new one
        if (composite.size() > 0)
            composite.clear()

        composite.add(
            dataRepository.getArtistName(artistName, page, apiKey)
                .subscribe({

                    if (it.results.artistmatches.artist.isEmpty())
                        shouldLoadMore = false

                    list.addAll(it.results.artistmatches.artist)
                    artistResponse.value = list
                    viewNotifier.value = ViewNotifierEnums.HIDE_LOADING
                }, {

                    Logger.d(it.message)
                    viewNotifier.value = ViewNotifierEnums.HIDE_LOADING
                    viewNotifier.value = ViewNotifierEnums.ERROR_GETTING_DATA
                })
        )
    }

    private fun prepareDataToSearch(isLoadMore: Boolean) {
        if (isLoadMore && shouldLoadMore) {
            page++
        } else {
            page = 1
            list.clear()
            shouldLoadMore = true
        }
    }

    fun getArtistResponse(): LiveData<List<Artist>> = artistResponse

    /**
     * Disposing all disposables after the ViewModel dies
     */
    override fun onCleared() {
        composite.dispose()
        super.onCleared()
    }
}
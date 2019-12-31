package ir.heydarii.musicmanager.features.searchartist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import ir.heydarii.musicmanager.base.BaseViewModel
import ir.heydarii.musicmanager.pojos.Artist
import ir.heydarii.musicmanager.repository.DataRepository
import ir.heydarii.musicmanager.utils.ViewNotifierEnums
import javax.inject.Inject

/**
 * ViewModel for the search artist view
 */
class SearchArtistViewModel @Inject constructor(val dataRepository: DataRepository) : BaseViewModel() {

    private val composite = CompositeDisposable()
    private val artistResponse = MutableLiveData<List<Artist>>()
    private var page = 1
    private var shouldLoadMore = true
    private var list = arrayListOf<Artist>()
    private var artistName = ""
    private var isLoading = false

    /**
     * Fetches all artists with the name that user enters
     */
    fun onUserSearchedArtist(artistName: String, apiKey: String, isLoadMore: Boolean) {

        if (isLoading) return

        if (artistName.isNotEmpty())
            this.artistName = artistName

        prepareDataToSearch(isLoadMore)

        viewNotifier.value = ViewNotifierEnums.SHOW_LOADING

        composite.add(
                dataRepository.getArtistName(this.artistName, page, apiKey)
                        .subscribe({
                            isLoading = false

                            if (it.results.artistmatches.artist.isEmpty())
                                shouldLoadMore = false

                            list.addAll(it.results.artistmatches.artist)
                            artistResponse.value = list
                            viewNotifier.value = ViewNotifierEnums.HIDE_LOADING
                        }, {

                            isLoading = false
                            Logger.d(it.message)
                            viewNotifier.value = ViewNotifierEnums.HIDE_LOADING
                            viewNotifier.value = ViewNotifierEnums.ERROR_GETTING_DATA
                        })
        )
    }

    private fun prepareDataToSearch(isLoadMore: Boolean) {
        isLoading = true
        if (isLoadMore && shouldLoadMore) {
            page++
        } else {
            page = 1
            list.clear()
            shouldLoadMore = true
        }
    }

    /**
     * provides immutable live data to be observed by the view
     */
    fun getArtistResponse(): LiveData<List<Artist>> = artistResponse

    /**
     * Disposing all disposables after the ViewModel dies
     */
    override fun onCleared() {
        composite.dispose()
        super.onCleared()
    }
}

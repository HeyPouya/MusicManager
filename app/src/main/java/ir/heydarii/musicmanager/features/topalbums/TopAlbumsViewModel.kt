package ir.heydarii.musicmanager.features.topalbums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import ir.heydarii.musicmanager.base.BaseViewModel
import ir.heydarii.musicmanager.pojos.Album
import ir.heydarii.musicmanager.repository.DataRepository
import ir.heydarii.musicmanager.utils.ViewNotifierEnums
import javax.inject.Inject

/**
 * ViewModel for TopAlbums of an Artist view
 */
class TopAlbumsViewModel @Inject constructor(private val dataRepository: DataRepository) : BaseViewModel() {

    private val topAlbumsData = MutableLiveData<List<Album>>()
    private val composite = CompositeDisposable()
    private var page = 1
    private val list = ArrayList<Album>()
    private var shouldLoadMore = true
    private var artist = ""

    /**
     * Fetches the top albums of a selected artist
     */
    fun onTopAlbumsRequested(artistName: String = artist, apiKey: String) {

        if (artistName.isNotEmpty())
            artist = artistName

        if (shouldLoadMore) {
            viewNotifier.value = ViewNotifierEnums.SHOW_LOADING

            composite.add(
                    dataRepository.getTopAlbumsByArtist(artistName, page, apiKey)
                            .subscribe({

                                if (it.topalbums.album.isEmpty())
                                    shouldLoadMore = false

                                page++
                                list.addAll(it.topalbums.album)
                                viewNotifier.value = ViewNotifierEnums.HIDE_LOADING
                                topAlbumsData.value = list
                            }, {
                                viewNotifier.value = ViewNotifierEnums.HIDE_LOADING
                                viewNotifier.value = ViewNotifierEnums.ERROR_GETTING_DATA
                                Logger.d(it)
                            })
            )
        }
    }

    /**
     * Serving LiveData instead of MutableLiveData for activity
     */
    fun getTopAlbumsLiveData(): LiveData<List<Album>> = topAlbumsData

    /**
     * Disposing all disposables after the ViewModel dies
     */
    override fun onCleared() {
        composite.dispose()
        super.onCleared()
    }
}

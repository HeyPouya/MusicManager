package ir.heydarii.musicmanager.features.savedalbums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import ir.heydarii.musicmanager.base.BaseViewModel
import ir.heydarii.musicmanager.base.di.DaggerDataRepositoryComponent
import ir.heydarii.musicmanager.pojos.AlbumDatabaseEntity
import ir.heydarii.musicmanager.repository.DataRepository
import ir.heydarii.musicmanager.utils.ViewNotifierEnums

class SavedAlbumsViewModel : BaseViewModel() {

    //TODO : Must be provided via viewModel Factory
    private val dataRepository: DataRepository = DaggerDataRepositoryComponent.create().getDataRepository()
    private val composite = CompositeDisposable()
    private val albumsList = MutableLiveData<List<AlbumDatabaseEntity>>()


    /**
     * Fetches all albums from database
     */
    fun getAllAlbums() {
        composite.add(
                dataRepository.getAllSavedAlbums()
                        .subscribe({
                            if (it.isEmpty())
                                viewNotifier.value = ViewNotifierEnums.EMPTY_STATE
                            else
                                albumsList.value = it
                        }, {
                            Logger.d(it)
                            viewNotifier.value = ViewNotifierEnums.ERROR_GETTING_DATA
                        })
        )
    }

    /**
     * returns an immutable instance of live data
     */
    fun getAlbumList(): LiveData<List<AlbumDatabaseEntity>> {
        return albumsList
    }


    /**
     * disposing the disposables
     */
    override fun onCleared() {
        super.onCleared()
        composite.dispose()
    }
}

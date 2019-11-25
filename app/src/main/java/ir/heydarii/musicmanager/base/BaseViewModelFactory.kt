package ir.heydarii.musicmanager.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.heydarii.musicmanager.features.albumdetails.AlbumDetailsViewModel
import ir.heydarii.musicmanager.features.savedalbums.SavedAlbumsViewModel
import ir.heydarii.musicmanager.features.searchartist.SearchArtistViewModel
import ir.heydarii.musicmanager.features.topalbums.TopAlbumsViewModel
import ir.heydarii.musicmanager.repository.DataRepository
import javax.inject.Inject

/**
 * ViewModelFactory to provide view models and pass the repository to them
 */
class BaseViewModelFactory @Inject constructor(val repository: DataRepository) : ViewModelProvider.Factory {

    /**
     * passes the repository to viewModels
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AlbumDetailsViewModel::class.java) -> AlbumDetailsViewModel(repository) as T
            modelClass.isAssignableFrom(SavedAlbumsViewModel::class.java) -> SavedAlbumsViewModel(repository) as T
            modelClass.isAssignableFrom(SearchArtistViewModel::class.java) -> SearchArtistViewModel(repository) as T
            modelClass.isAssignableFrom(TopAlbumsViewModel::class.java) -> TopAlbumsViewModel(repository) as T
            else -> throw IllegalStateException("No such ViewModel class ${modelClass.name}")
        }

    }
}
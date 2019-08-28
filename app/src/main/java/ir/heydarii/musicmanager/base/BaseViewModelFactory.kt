package ir.heydarii.musicmanager.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.heydarii.musicmanager.base.di.DaggerDataRepositoryComponent
import ir.heydarii.musicmanager.features.albumdetails.AlbumDetailsViewModel
import ir.heydarii.musicmanager.features.savedalbums.SavedAlbumsViewModel
import ir.heydarii.musicmanager.features.searchartist.SearchArtistViewModel
import ir.heydarii.musicmanager.features.topalbums.TopAlbumsViewModel
import javax.inject.Inject

class BaseViewModelFactory @Inject constructor() :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

      val repository =   DaggerDataRepositoryComponent.create().getDataRepository()
        return when {
            modelClass.isAssignableFrom(AlbumDetailsViewModel::class.java) -> AlbumDetailsViewModel(repository) as T
            modelClass.isAssignableFrom(SavedAlbumsViewModel::class.java) -> SavedAlbumsViewModel(repository) as T
            modelClass.isAssignableFrom(SearchArtistViewModel::class.java) -> SearchArtistViewModel(repository) as T
            modelClass.isAssignableFrom(TopAlbumsViewModel::class.java) -> TopAlbumsViewModel(repository) as T
            else -> throw IllegalStateException("No such ViewModel class")
        }

    }
}
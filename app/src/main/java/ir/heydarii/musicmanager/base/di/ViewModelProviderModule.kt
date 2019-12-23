package ir.heydarii.musicmanager.base.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ir.heydarii.musicmanager.features.albumdetails.AlbumDetailsViewModel
import ir.heydarii.musicmanager.features.savedalbums.SavedAlbumsViewModel
import ir.heydarii.musicmanager.features.searchartist.SearchArtistViewModel
import ir.heydarii.musicmanager.features.topalbums.TopAlbumsViewModel

/**
 * Provides ViewModels to let Dagger inject them
 */
@Module
abstract class ViewModelProviderModule {

    @Binds
    @IntoMap
    @ViewModelKey(AlbumDetailsViewModel::class)
    abstract fun bindViewModel(viewModel: AlbumDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SavedAlbumsViewModel::class)
    abstract fun SavedAlbumsViewModelProvider(viewModel: SavedAlbumsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchArtistViewModel::class)
    abstract fun SearchArtistViewModelProvider(viewModel: SearchArtistViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TopAlbumsViewModel::class)
    abstract fun TopAlbumsViewModelProvider(viewModel: TopAlbumsViewModel): ViewModel


}
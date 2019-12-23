package ir.heydarii.musicmanager.base.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ir.heydarii.musicmanager.features.about.AboutMeFragment
import ir.heydarii.musicmanager.features.albumdetails.AlbumDetailsFragment
import ir.heydarii.musicmanager.features.savedalbums.SavedAlbumsFragment
import ir.heydarii.musicmanager.features.searchartist.SearchArtistFragment
import ir.heydarii.musicmanager.features.topalbums.TopAlbumsFragment

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun aboutMeFragmentContributor(): AboutMeFragment

    @ContributesAndroidInjector
    abstract fun albumDetailsFragmentContributor(): AlbumDetailsFragment

    @ContributesAndroidInjector
    abstract fun savedAlbumsFragmentContributor(): SavedAlbumsFragment

    @ContributesAndroidInjector
    abstract fun searchArtistFragmentContributor(): SearchArtistFragment

    @ContributesAndroidInjector
    abstract fun topAlbumsFragmentContributor(): TopAlbumsFragment
}
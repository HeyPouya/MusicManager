package ir.heydarii.musicmanager.framework.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.heydarii.musicmanager.framework.ArtistDataSource
import ir.heydarii.musicmanager.framework.network.RetrofitAlbumsInterface
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ArtistDataSourceModule {

    @Singleton
    @Provides
    fun provideAlbumsDataSource(artistRetrofit: RetrofitAlbumsInterface): com.pouyaheydari.android.core.data.ArtistDataSource =
        ArtistDataSource(artistRetrofit)
}

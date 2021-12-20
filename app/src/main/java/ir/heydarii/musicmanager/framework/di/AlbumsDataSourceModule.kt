package ir.heydarii.musicmanager.framework.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.heydarii.musicmanager.framework.AlbumsDataSource
import ir.heydarii.musicmanager.framework.db.AlbumsDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AlbumsDataSourceModule {

    @Singleton
    @Provides
    fun provideAlbumsDataSource(albumsDao: AlbumsDao): com.pouyaheydari.android.core.data.AlbumsDataSource =
        AlbumsDataSource(albumsDao)
}

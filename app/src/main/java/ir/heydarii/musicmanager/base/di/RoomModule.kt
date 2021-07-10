package ir.heydarii.musicmanager.base.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.heydarii.musicmanager.base.AppDatabase
import ir.heydarii.musicmanager.repository.local.AlbumsDAO
import javax.inject.Singleton

private const val DATABASE_NAME = "albums.db"

/**
 * Provides room database
 */
@InstallIn(SingletonComponent::class)
@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application): AppDatabase = Room.databaseBuilder(
        application.applicationContext,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideAlbumsDAO(appDatabase: AppDatabase): AlbumsDAO = appDatabase.albumsDAO()
}

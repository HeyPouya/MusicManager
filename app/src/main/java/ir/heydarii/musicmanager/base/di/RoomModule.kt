package ir.heydarii.musicmanager.base.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ir.heydarii.musicmanager.repository.dbinteractor.AlbumsDAO
import ir.heydarii.musicmanager.utils.AppDatabase
import javax.inject.Singleton

/**
 * A module to provide all dependencies for room
 */
@Module
class RoomModule {

    @Singleton
    @Provides
    fun database(application: Application): AppDatabase {
        return Room.databaseBuilder(application.applicationContext, AppDatabase::class.java, "Albums.db").build()
    }

    @Singleton
    @Provides
    fun provideAlbumsDAO(appDatabase: AppDatabase): AlbumsDAO {
        return appDatabase.albumsDAO()
    }

}
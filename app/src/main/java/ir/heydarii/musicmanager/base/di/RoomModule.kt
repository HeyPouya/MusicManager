package ir.heydarii.musicmanager.base.di

import androidx.room.Room
import dagger.Module
import dagger.Provides
import ir.heydarii.musicmanager.base.BaseApplication
import ir.heydarii.musicmanager.repository.dbinteractor.AlbumsDAO
import ir.heydarii.musicmanager.utils.AppDatabase
import javax.inject.Singleton


@Module
class RoomModule(val baseApplication: BaseApplication) {

    @Singleton
    @Provides
    fun database(): AppDatabase {
        return Room.databaseBuilder(baseApplication.applicationContext, AppDatabase::class.java, "Albums.db").build()

    }

    @Singleton
    @Provides
    fun provideAlbumsDAO(appDatabase: AppDatabase): AlbumsDAO {
        return appDatabase.albumsDAO()
    }

}
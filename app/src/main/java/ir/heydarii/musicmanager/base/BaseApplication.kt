package ir.heydarii.musicmanager.base

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import ir.heydarii.musicmanager.base.di.DaggerDataProvidersComponent
import ir.heydarii.musicmanager.base.di.DataProvidersComponent
import ir.heydarii.musicmanager.base.di.RoomModule


/*
Base application class to provide a singleton object of DataProviders
 */
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Logger.addLogAdapter(AndroidLogAdapter())

        component = DaggerDataProvidersComponent.builder().roomModule(RoomModule(this)).build()

    }

    companion object {
        private lateinit var component: DataProvidersComponent


        /**
         * Provides DataProvidersComponent
         */
        fun getDataProviderComponent(): DataProvidersComponent {
            return component
        }

    }
}
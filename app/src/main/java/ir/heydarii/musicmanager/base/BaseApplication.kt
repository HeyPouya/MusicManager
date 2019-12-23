package ir.heydarii.musicmanager.base

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import ir.heydarii.musicmanager.base.di.DaggerDataProvidersComponent
import ir.heydarii.musicmanager.base.di.DataProvidersComponent
import ir.heydarii.musicmanager.base.di.RoomModule


/**
 * Base application class to provide a singleton object of DataProviders
 */
class BaseApplication : DaggerApplication() {
    override fun onCreate() {
        super.onCreate()

        Logger.addLogAdapter(AndroidLogAdapter())

        component = DaggerDataProvidersComponent.builder().roomModule(RoomModule(this)).build()

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
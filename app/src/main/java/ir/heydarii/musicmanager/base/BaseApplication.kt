package ir.heydarii.musicmanager.base

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.android.support.DaggerApplication
import ir.heydarii.musicmanager.base.di.DaggerAppComponent


/**
 * Base application class to provide a singleton object of DataProviders
 */
class BaseApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    override fun applicationInjector() = DaggerAppComponent.builder().application(this).build()
}
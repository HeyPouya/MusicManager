package ir.heydarii.musicmanager.base

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import ir.heydarii.musicmanager.base.di.DaggerRetrofitComponent
import ir.heydarii.musicmanager.base.di.RetrofitComponent


class BaseApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        Logger.addLogAdapter(AndroidLogAdapter())

    }

    companion object {
        private val component = DaggerRetrofitComponent.create()

        fun getRetrofitComponent(): RetrofitComponent {
            return component
        }
    }
}
package ir.heydarii.musicmanager.base

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import ir.heydarii.musicmanager.base.di.DaggerRetrofitComponent
import ir.heydarii.musicmanager.base.di.RetrofitComponent
import ir.heydarii.musicmanager.base.di.RoomModule


class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Logger.addLogAdapter(AndroidLogAdapter())

        component = DaggerRetrofitComponent.builder().roomModule(RoomModule(this)).build()

    }

    companion object {
        private lateinit var component: RetrofitComponent


        fun getRetrofitComponent(): RetrofitComponent {
            return component
        }

    }
}
package ir.heydarii.musicmanager.base

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import ir.heydarii.musicmanager.base.di.DaggerRetrofitComponent
import ir.heydarii.musicmanager.retrofit.RetrofitMainInterface
import retrofit2.Retrofit
import javax.inject.Inject


class BaseApplication : Application() {

    @Inject
    lateinit var retrofit: Retrofit

    override fun onCreate() {
        super.onCreate()

        Logger.addLogAdapter(AndroidLogAdapter())

        DaggerRetrofitComponent.create().inject(this)
        retrofit.create(RetrofitMainInterface::class.java)
    }


}
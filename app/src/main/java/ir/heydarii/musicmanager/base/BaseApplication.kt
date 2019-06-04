package ir.heydarii.musicmanager.base

import android.app.Application
import ir.heydarii.musicmanager.base.di.DaggerRetrofitComponent
import ir.heydarii.musicmanager.retrofit.RetrofitMainInterface
import retrofit2.Retrofit
import javax.inject.Inject

class BaseApplication : Application() {

    @Inject
    lateinit var retrofit: Retrofit

    override fun onCreate() {
        super.onCreate()

        DaggerRetrofitComponent.create().inject(this)
        retrofit.create(RetrofitMainInterface::class.java)
    }


}
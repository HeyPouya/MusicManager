package ir.heydarii.musicmanager.base.di

import dagger.Component
import ir.heydarii.musicmanager.base.BaseApplication
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class])
interface RetrofitComponent {

    fun getRetrofit(): Retrofit

    fun inject(baseApplication: BaseApplication)
}
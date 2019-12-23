package ir.heydarii.musicmanager.base.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ir.heydarii.musicmanager.base.BaseApplication
import javax.inject.Singleton

/**
 * A dagger component to provide repository for the project
 */
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ActivityBuilderModule::class, RetrofitModule::class, RoomModule::class, ViewModelFactoryProvider::class])
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
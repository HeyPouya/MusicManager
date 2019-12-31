package ir.heydarii.musicmanager.base.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ir.heydarii.musicmanager.base.BaseApplication
import javax.inject.Singleton

/**
 * Main dagger component to provide dependencies for app
 */
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ActivityBuilderModule::class, RetrofitModule::class, RoomModule::class, ViewModelFactoryProvider::class])
interface AppComponent : AndroidInjector<BaseApplication> {

    /**
     * Builder to bind application to modules
     */
    @Component.Builder
    interface Builder {

        /**
         * Gets application instance
         */
        @BindsInstance
        fun application(application: Application): Builder

        /**
         * Builds the component
         */
        fun build(): AppComponent
    }
}

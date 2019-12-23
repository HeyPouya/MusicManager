package ir.heydarii.musicmanager.base.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ir.heydarii.musicmanager.features.MainActivity

/**
 * Builds and injects needed activities by Dagger 2
 */
@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [FragmentBuilderModule::class, ViewModelProviderModule::class])
    abstract fun mainActivityContributor(): MainActivity
}
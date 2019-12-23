package ir.heydarii.musicmanager.base.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import ir.heydarii.musicmanager.base.ViewModelFactory

/**
 * Provides ViewModelFactory
 */
@Module
abstract class ViewModelFactoryProvider {

    @Binds
    abstract fun viewModelFactoryProvider(factory: ViewModelFactory): ViewModelProvider.Factory

}
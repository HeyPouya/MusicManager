package ir.heydarii.musicmanager.repository.di

import dagger.Component
import ir.heydarii.musicmanager.CustomScope
import ir.heydarii.musicmanager.base.di.DataProvidersComponent
import ir.heydarii.musicmanager.repository.dbinteractor.AlbumsDAO
import ir.heydarii.musicmanager.repository.networkinteractor.NetworkInteractor

/**
 * This class provides network and database classes for the repository class
 */
@CustomScope
@Component(dependencies = [DataProvidersComponent::class])
interface DataProviderComponent {

    /**
     * Provides network interactor for project by Dagger
     */
    fun getNetworkInteractor(): NetworkInteractor

    /**
     * Provides room db interactor by Dagger for project
     */
    fun getDbInteractor(): AlbumsDAO
}
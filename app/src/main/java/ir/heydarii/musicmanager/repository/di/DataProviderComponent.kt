package ir.heydarii.musicmanager.repository.di

import dagger.Component
import ir.heydarii.musicmanager.CustomScope
import ir.heydarii.musicmanager.base.di.RetrofitComponent
import ir.heydarii.musicmanager.repository.DataRepository
import ir.heydarii.musicmanager.repository.dbinteractor.AlbumsDAO
import ir.heydarii.musicmanager.repository.networkinteractor.NetworkInteractor

@CustomScope
@Component(dependencies = [RetrofitComponent::class])
interface DataProviderComponent {

    fun getNetworkInteractor(): NetworkInteractor

    fun getDbInteractor(): AlbumsDAO
}
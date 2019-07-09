package ir.heydarii.musicmanager.base.di

import dagger.Component
import ir.heydarii.musicmanager.repository.DataRepository


@Component
interface DataRepositoryComponent {

    fun getDataRepository(): DataRepository
}
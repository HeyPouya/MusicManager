package ir.heydarii.musicmanager.base.di

import dagger.Component
import ir.heydarii.musicmanager.repository.DataRepository

/*
A dagger component to provide repository for the project
 */
@Component
interface DataRepositoryComponent {

    fun getDataRepository(): DataRepository
}
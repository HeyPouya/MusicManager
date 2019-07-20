package ir.heydarii.musicmanager.features.albumdetails.di

import dagger.Component
import ir.heydarii.musicmanager.utils.ImageStorageManager

@Component
interface ImageStorageComponent {

    fun getImageStorageManager() : ImageStorageManager
}
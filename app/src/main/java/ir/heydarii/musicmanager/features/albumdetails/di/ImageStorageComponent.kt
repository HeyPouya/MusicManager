package ir.heydarii.musicmanager.features.albumdetails.di

import dagger.Component
import ir.heydarii.musicmanager.utils.ImageStorageManager

/**
 * Dagger interface to provide image storage
 */
@Component
interface ImageStorageComponent {

    /**
     * Image storage provider
     */
    fun getImageStorageManager() : ImageStorageManager
}
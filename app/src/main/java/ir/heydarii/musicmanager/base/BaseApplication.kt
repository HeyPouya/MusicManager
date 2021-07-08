package ir.heydarii.musicmanager.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Base application class to provide a singleton object of DataProviders
 */
@HiltAndroidApp
class BaseApplication : Application()

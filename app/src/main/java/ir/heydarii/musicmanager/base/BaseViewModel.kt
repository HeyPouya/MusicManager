package ir.heydarii.musicmanager.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.heydarii.musicmanager.utils.ViewNotifierEnums
import javax.inject.Inject

/**
 * All ViewModels inherit this class, so we can do some common jobs in them
 */
open class BaseViewModel : ViewModel() {

    // a mutable live data to interact with view
    protected val viewNotifier = MutableLiveData<ViewNotifierEnums>()

    /**
     * Provides a LiveData for View to subscribe on this and get updated
     */
    fun getViewNotifier(): LiveData<ViewNotifierEnums> = viewNotifier
}

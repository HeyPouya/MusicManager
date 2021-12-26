package ir.heydarii.musicmanager.presentation

import androidx.lifecycle.ViewModel
import ir.heydarii.musicmanager.presentation.utils.ErrorTypes
import ir.heydarii.musicmanager.presentation.utils.SingleLiveEvent

/**
 * All ViewModels inherit this class, so we can do some common jobs in them
 */
open class BaseViewModel : ViewModel() {
    val errorLiveData = SingleLiveEvent<ErrorTypes>()
}

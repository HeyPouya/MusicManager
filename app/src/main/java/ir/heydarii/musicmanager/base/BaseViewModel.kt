package ir.heydarii.musicmanager.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.heydarii.musicmanager.utils.ViewNotifierEnums

open class BaseViewModel : ViewModel() {


    protected val viewNotifier = MutableLiveData<ViewNotifierEnums>()
    fun getViewNotifier(): LiveData<ViewNotifierEnums> = viewNotifier

}
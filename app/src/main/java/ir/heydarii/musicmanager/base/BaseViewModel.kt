package ir.heydarii.musicmanager.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    protected val loadingData = MutableLiveData<Int>()
    fun getLoadingData(): LiveData<Int> = loadingData

}
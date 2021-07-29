package ir.heydarii.musicmanager.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.heydarii.musicmanager.pojos.base.ErrorTypes
import ir.heydarii.musicmanager.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import java.util.concurrent.TimeoutException

/**
 * All ViewModels inherit this class, so we can do some common jobs in them
 */
open class BaseViewModel : ViewModel() {
    private val errorLiveData = SingleLiveEvent<ErrorTypes>()

    fun coroutinesExceptionHandler() = CoroutineExceptionHandler { _, throwable ->
        println(throwable)
        when (throwable) {
            is IOException, is TimeoutException -> errorLiveData.postValue(ErrorTypes.IOError)
            is HttpException -> errorLiveData.postValue(ErrorTypes.ServerError)
            else -> errorLiveData.postValue(ErrorTypes.UnknownError)
        }
    }

    /**
     * @return [androidx.lifecycle.LiveData] version of [errorLiveData]
     */
    fun getErrorLiveData(): SingleLiveEvent<ErrorTypes> = errorLiveData

    fun launch(block: suspend () -> Unit) =
        viewModelScope.launch(IO + coroutinesExceptionHandler()) {
            block()
        }
}

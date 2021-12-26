package ir.heydarii.musicmanager.framework

import androidx.lifecycle.ViewModel
import ir.heydarii.musicmanager.presentation.ErrorTypes
import ir.heydarii.musicmanager.presentation.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import okio.IOException
import retrofit2.HttpException
import java.util.concurrent.TimeoutException

/**
 * All ViewModels inherit this class, so we can do some common jobs in them
 */
open class BaseViewModel : ViewModel() {
    private val errorLiveData = SingleLiveEvent<ErrorTypes>()

    protected fun coroutinesExceptionHandler() = CoroutineExceptionHandler { _, throwable ->
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
}

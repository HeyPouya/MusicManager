package ir.heydarii.musicmanager.presentation.utils

import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

fun coroutinesExceptionHandler(errorLiveData: SingleLiveEvent<ErrorTypes>) =
    CoroutineExceptionHandler { _, throwable ->
        println(throwable)
        when (throwable) {
            is IOException, is TimeoutException -> errorLiveData.postValue(ErrorTypes.IOError)
            is HttpException -> errorLiveData.postValue(ErrorTypes.ServerError)
            else -> errorLiveData.postValue(ErrorTypes.UnknownError)
        }
    }
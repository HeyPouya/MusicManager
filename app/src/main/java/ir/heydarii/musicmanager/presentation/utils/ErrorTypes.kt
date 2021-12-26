package ir.heydarii.musicmanager.presentation.utils

/**
 * A sealed class to exchange different error between [BaseViewModel] and [BaseFragment]
 */
sealed class ErrorTypes {
    object IOError : ErrorTypes()
    object UnknownError : ErrorTypes()
    object ServerError : ErrorTypes()
}
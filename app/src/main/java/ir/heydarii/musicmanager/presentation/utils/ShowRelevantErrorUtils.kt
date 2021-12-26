package ir.heydarii.musicmanager.presentation.utils

import android.view.View
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.presentation.utils.ErrorTypes.*

fun showRelevantError(errorType: ErrorTypes?, view: View) {
    when (errorType) {
        IOError -> showError(view.context.getString(R.string.internet_connection_error), view)
        ServerError -> showError(view.context.getString(R.string.server_connection_error), view)
        UnknownError -> showError(view.context.getString(R.string.please_try_again), view)
        null -> showError(view.context.getString(R.string.please_try_again), view)
    }
}
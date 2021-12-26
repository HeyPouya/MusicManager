package ir.heydarii.musicmanager.presentation.utils

import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import ir.heydarii.musicmanager.R

fun showError(message: String, view: View) {
    val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    snackbar.setBackgroundTint(ContextCompat.getColor(view.context, R.color.red_400))
    snackbar.show()
}
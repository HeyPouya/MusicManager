package ir.heydarii.musicmanager.utils.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File

private const val NO_PLACEHOLDER = -1

fun ImageView.load(path: String, placeholder: Int = NO_PLACEHOLDER) {
    when {
        path.isEmpty() -> return
        path.startsWith("http") -> loadUrl(this, path, placeholder)
        else -> loadFile(this, path, placeholder)
    }
}

private fun loadUrl(imgView: ImageView, url: String, placeholder: Int) {
    val glide = Glide.with(imgView).load(url)
    if (placeholder != NO_PLACEHOLDER)
        glide.placeholder(placeholder)
    glide.into(imgView)
}

private fun loadFile(imgView: ImageView, filePath: String, placeholder: Int) {
    val file = File(filePath)
    val glide = Glide.with(imgView).load(file)
    if (placeholder != NO_PLACEHOLDER)
        glide.placeholder(placeholder)
    glide.into(imgView)
}

fun View.showKeyboard() {
    val imm: InputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    val imm: InputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun EditText.onEditorImeAction(action: Int = EditorInfo.IME_ACTION_DONE, block: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == action) {
            block()
            return@setOnEditorActionListener true
        }
        false
    }
}

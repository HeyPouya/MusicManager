package ir.heydarii.musicmanager.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File

private const val NO_PLACEHOLDER = -1

fun ImageView.loadUrl(url: String, placeholder: Int = NO_PLACEHOLDER) {
    val glide = Glide.with(this).load(url)
    if (placeholder != NO_PLACEHOLDER)
        glide.placeholder(placeholder)
    glide.into(this)
}

fun ImageView.loadFile(file: File, placeholder: Int = NO_PLACEHOLDER) {
    val glide = Glide.with(this).load(file)
    if (placeholder != NO_PLACEHOLDER)
        glide.placeholder(placeholder)
    glide.into(this)
}
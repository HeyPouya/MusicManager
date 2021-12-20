package ir.heydarii.musicmanager.presentation.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

private const val NO_PLACEHOLDER = -1

fun ImageView.load(url: String, placeholder: Int = NO_PLACEHOLDER) {
    val glide = Glide.with(this).load(url).also {
        if (placeholder != NO_PLACEHOLDER)
            it.placeholder(placeholder)
    }
    glide.into(this)
}
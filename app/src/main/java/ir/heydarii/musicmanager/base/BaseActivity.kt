package ir.heydarii.musicmanager.base

import androidx.appcompat.app.AppCompatActivity
import dagger.android.support.DaggerAppCompatActivity

/**
 * All activities inherit this class, so our hands will be open in the future
 * to do some common things in them
 */
open class BaseActivity : DaggerAppCompatActivity()
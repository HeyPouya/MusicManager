package ir.heydarii.musicmanager.base

import androidx.fragment.app.Fragment
import dagger.android.support.DaggerFragment

/**
 * All Fragments inherit this class, so our hands will be open in the future
 * to do some common things in them
 */
open class BaseFragment : DaggerFragment()
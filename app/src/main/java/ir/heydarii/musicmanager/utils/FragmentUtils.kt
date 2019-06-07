package ir.heydarii.musicmanager.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/*
 * This class is provided to help doing some complex tasks with fragments
 * And prevent our views to be messy
 */
class FragmentUtils {

    companion object {

        /**
         * Adds a fragment to an specific container and then hides it immediately
         */
        fun addAndHideFragments(fm: FragmentManager, container: Int, fragment: Fragment) {
            val manager = fm.beginTransaction()
            manager.add(container, fragment, fragment::class.java.simpleName)
            manager.hide(fragment)
            manager.commit()
        }
    }
}
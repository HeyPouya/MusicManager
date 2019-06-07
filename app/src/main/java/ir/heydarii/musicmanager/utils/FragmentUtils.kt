package ir.heydarii.musicmanager.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentUtils {

    companion object {
        fun addAndHideFragments(fm: FragmentManager, container: Int, fragment: Fragment) {
            val manager = fm.beginTransaction()
            manager.add(container, fragment, fragment::class.java.simpleName)
            manager.hide(fragment)
            manager.commit()
        }
    }
}
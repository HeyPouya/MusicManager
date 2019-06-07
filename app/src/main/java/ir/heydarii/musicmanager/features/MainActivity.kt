package ir.heydarii.musicmanager.features

import android.os.Bundle
import androidx.fragment.app.Fragment
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseActivity
import ir.heydarii.musicmanager.features.about.AboutMeFragment
import ir.heydarii.musicmanager.features.searchpage.SearchArtistFragment
import ir.heydarii.musicmanager.utils.Consts.Companion.CURRENT_FRAGMENT
import ir.heydarii.musicmanager.utils.FragmentUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var searchFragment: SearchArtistFragment
    private lateinit var aboutMeFragment: AboutMeFragment
    private var currentFragment: Fragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //TODO : // clean the fragments
        if (savedInstanceState == null) {
            setUpFragments()
            addFragmentsToLayout()
        } else
            retainFragments(savedInstanceState.getString(CURRENT_FRAGMENT))

        setUpBottomNav()
    }

    private fun addFragmentsToLayout() {
        FragmentUtils.addAndHideFragments(supportFragmentManager, R.id.container, searchFragment)
        FragmentUtils.addAndHideFragments(supportFragmentManager, R.id.container, aboutMeFragment)
    }

    private fun retainFragments(fragment: String?) {
        searchFragment =
                supportFragmentManager.findFragmentByTag(SearchArtistFragment::class.java.simpleName) as SearchArtistFragment
        aboutMeFragment = supportFragmentManager.findFragmentByTag(AboutMeFragment::class.java.simpleName) as AboutMeFragment

        when (fragment) {
            SearchArtistFragment::class.java.simpleName -> currentFragment = searchFragment
            AboutMeFragment::class.java.simpleName -> currentFragment = aboutMeFragment
        }


    }

    private fun setUpFragments() {
        searchFragment = SearchArtistFragment.newInstance()
        aboutMeFragment = AboutMeFragment.newInstance()
    }

    private fun setUpBottomNav() {
        bottomNav.inflateMenu(R.menu.bottom_nav_menu)
        bottomNav.setOnNavigationItemSelectedListener {
            showFragment(it.itemId)
            true
        }
    }


    private fun showFragment(id: Int) {
        when (id) {
            R.id.search -> {
                displayOrHideFragments(searchFragment)
            }
            R.id.home -> {
            }
            R.id.about -> {
                displayOrHideFragments(aboutMeFragment)
            }
        }

    }

    private fun displayOrHideFragments(clickedFragment: Fragment) {
        val manager = supportFragmentManager.beginTransaction()

        if (currentFragment == clickedFragment)
            return
        else if (clickedFragment.isAdded && currentFragment == null) {
            manager.show(clickedFragment)
        } else if (clickedFragment.isAdded && currentFragment != null) {
            manager.hide(currentFragment!!)
            manager.show(clickedFragment)
        }

        manager.commit()
        currentFragment = clickedFragment


    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(CURRENT_FRAGMENT, currentFragment?.tag)
        super.onSaveInstanceState(outState)
    }
}

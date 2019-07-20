package ir.heydarii.musicmanager.features

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseActivity
import ir.heydarii.musicmanager.features.about.AboutMeFragment
import ir.heydarii.musicmanager.features.savedalbums.SavedAlbumsFragment
import ir.heydarii.musicmanager.features.searchartist.SearchArtistFragment
import ir.heydarii.musicmanager.utils.Consts.Companion.CURRENT_FRAGMENT
import ir.heydarii.musicmanager.utils.FragmentUtils
import kotlinx.android.synthetic.main.activity_main.*

/*
The main activity that shows 3 main fragments
1. SavedAlbums
2. SearchArtist
3. About
 */
class MainActivity : BaseActivity() {

    private lateinit var searchFragment: SearchArtistFragment
    private lateinit var aboutMeFragment: AboutMeFragment
    private lateinit var savedAlbumsFragment: SavedAlbumsFragment
    private var currentFragment: Fragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //We don't want to ruin everything on configuration change
        if (savedInstanceState == null) {
            setUpFragments()
            addFragmentsToLayout()
        } else
            retainFragments(savedInstanceState.getString(CURRENT_FRAGMENT))

        setUpBottomNav()
    }


    /**
     * Adds all fragments to the container once the user opens the app
     */
    private fun addFragmentsToLayout() {
        FragmentUtils.addAndHideFragments(supportFragmentManager, R.id.container, savedAlbumsFragment)
        FragmentUtils.addAndHideFragments(supportFragmentManager, R.id.container, searchFragment)
        FragmentUtils.addAndHideFragments(supportFragmentManager, R.id.container, aboutMeFragment)

        displayFirstTab()
    }

    /**
     * Some delays to make sure that all fragments have added successfully to the container
     */
    private fun displayFirstTab() {
        Handler().postDelayed({ displayFragments(savedAlbumsFragment) }
            , 200)
    }

    /**
     * Retains all fragments after rotation, so we wont make new fragments
     */
    private fun retainFragments(fragment: String?) {
        searchFragment =
            supportFragmentManager.findFragmentByTag(SearchArtistFragment::class.java.simpleName) as SearchArtistFragment
        aboutMeFragment =
            supportFragmentManager.findFragmentByTag(AboutMeFragment::class.java.simpleName) as AboutMeFragment
        savedAlbumsFragment =
            supportFragmentManager.findFragmentByTag(SavedAlbumsFragment::class.java.simpleName) as SavedAlbumsFragment

        when (fragment) {
            SearchArtistFragment::class.java.simpleName -> currentFragment = searchFragment
            AboutMeFragment::class.java.simpleName -> currentFragment = aboutMeFragment
            SavedAlbumsFragment::class.java.simpleName -> currentFragment = savedAlbumsFragment
        }


    }

    /**
     * gets a new instance of fragments only once
     */
    //TODO : Provide the fragments using dagger
    private fun setUpFragments() {
        searchFragment = SearchArtistFragment.newInstance()
        aboutMeFragment = AboutMeFragment.newInstance()
        savedAlbumsFragment = SavedAlbumsFragment.newInstance()
    }

    /**
     * SetonItemListener fot bottomNavigation menu
     */
    private fun setUpBottomNav() {
        bottomNav.setOnNavigationItemSelectedListener {
            showFragment(it.itemId)
            true
        }
    }

    /**
     * Decides witch fragment should be displayed based on what user has selected
     * from the bottom navigation menu
     */
    private fun showFragment(id: Int) {
        when (id) {
            R.id.search -> displayFragments(searchFragment)
            R.id.home -> displayFragments(savedAlbumsFragment)
            R.id.about -> displayFragments(aboutMeFragment)
        }
    }

    /**
     * Displays the requested fragment and hides other fragments
     */
    private fun displayFragments(clickedFragment: Fragment) {
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

    /**
     * Saves the latest fragment that is showing to user before rotation,
     * so after rotation we wont loos the control
     */
    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(CURRENT_FRAGMENT, currentFragment?.tag)
        super.onSaveInstanceState(outState)
    }
}

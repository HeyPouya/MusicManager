package ir.heydarii.musicmanager.features

import android.os.Bundle
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseActivity
import ir.heydarii.musicmanager.features.searchpage.SearchArtistFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var searchFragment: SearchArtistFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //TODO : clean the fragment mess :D


        if (savedInstanceState == null)
            setUpFragments()
        else
            retainFragments()

        setUpBottomNav()
    }

    private fun retainFragments() {
        searchFragment =
            supportFragmentManager.findFragmentByTag(SearchArtistFragment::class.java.simpleName) as SearchArtistFragment
    }

    private fun setUpFragments() {
        searchFragment = SearchArtistFragment.newInstance()
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
                val manager = supportFragmentManager.beginTransaction()
                manager.replace(R.id.container, searchFragment, SearchArtistFragment::class.java.simpleName)
                manager.commit()
            }
            R.id.home -> {
            }
            R.id.top -> {
            }
        }

    }
}

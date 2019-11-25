package ir.heydarii.musicmanager.features

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 *
 * The main activity that shows 3 main fragments
 * 1. SavedAlbums
 * 2. SearchArtist
 * 3. About
 *
 */
class MainActivity : BaseActivity() {

    /**
     * finding the navController
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nabController = Navigation.findNavController(this, R.id.container)

        setUpBottomNav(nabController)
    }

    private fun setUpBottomNav(navController: NavController) {
        bottomNav.setupWithNavController(navController)
    }
}

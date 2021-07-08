package ir.heydarii.musicmanager.features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import ir.heydarii.musicmanager.R

/**
 * The main activity that shows 3 main fragments
 * 1. SavedAlbums
 * 2. SearchArtist
 * 3. About
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    /**
     * finding the navController
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainNavContainer) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottomNav).setupWithNavController(navController)
    }
}

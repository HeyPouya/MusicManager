package ir.heydarii.musicmanager.features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.databinding.ActivityMainBinding

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

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainNavContainer) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
    }
}

package ir.heydarii.musicmanager.presentation.features.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.databinding.FragmentAboutMeBinding

/**
 * Shows some about me info
 */

private const val GITHUB_URL = "https://github.com/SirLordPouya/MusicManager"
private const val PERSONAL_WEBSITE_URL = "https://pouyaheydari.com"
private const val LINKED_IN_URL = "https://linkedin.com/in/pouyaheydari/"

@AndroidEntryPoint
class AboutMeFragment : Fragment(),
    View.OnClickListener {

    private lateinit var binding: FragmentAboutMeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutMeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            imgGithub.setOnClickListener(this@AboutMeFragment)
            imgLinkedin.setOnClickListener(this@AboutMeFragment)
            imgWebsite.setOnClickListener(this@AboutMeFragment)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imgGithub ->
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_URL)))
            R.id.imgLinkedin ->
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(LINKED_IN_URL)))
            R.id.imgWebsite ->
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(PERSONAL_WEBSITE_URL)))
        }
    }
}

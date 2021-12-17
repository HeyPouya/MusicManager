package ir.heydarii.musicmanager.features.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseFragment
import ir.heydarii.musicmanager.base.BaseViewModel
import ir.heydarii.musicmanager.databinding.FragmentAboutMeBinding

/**
 * Shows some about me info
 */

private const val GITHUB_URL = "https://github.com/SirLordPouya/MusicManager"
private const val PERSONAL_WEBSITE_URL = "https://pouyaheydari.com"
private const val LINKED_IN_URL = "https://linkedin.com/in/pouyaheydari/"

@AndroidEntryPoint
class AboutMeFragment : BaseFragment<FragmentAboutMeBinding, BaseViewModel>(),
    View.OnClickListener {

    override var layout = R.layout.fragment_about_me
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

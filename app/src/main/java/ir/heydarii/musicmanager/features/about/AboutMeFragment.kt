package ir.heydarii.musicmanager.features.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseFragment
import ir.heydarii.musicmanager.utils.Constants
import kotlinx.android.synthetic.main.fragment_about_me.*

/**
 * A fragment only to show some about me info
 */
@AndroidEntryPoint
class AboutMeFragment : BaseFragment(), View.OnClickListener {

    /**
     * inflates the layout
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about_me, container, false)
    }

    /**
     * sets up click listeners
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgGithub.setOnClickListener(this)
        imgLinkedin.setOnClickListener(this)
        imgWebsite.setOnClickListener(this)
    }

    /**
     * performs clicks on social buttons
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imgGithub -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.GITHUB_URL)))
            R.id.imgLinkedin -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.LINKED_IN_URL)))
            R.id.imgWebsite -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.PERSONAL_WEBSITE_URL)))
        }
    }
}

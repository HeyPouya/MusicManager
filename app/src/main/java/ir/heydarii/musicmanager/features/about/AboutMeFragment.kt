package ir.heydarii.musicmanager.features.about


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseFragment
import ir.heydarii.musicmanager.utils.Consts
import kotlinx.android.synthetic.main.fragment_about_me.view.*

/*
 * A dummy fragment only to show some about me info
 */
class AboutMeFragment : BaseFragment() {

    companion object {
        fun newInstance() = AboutMeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_me, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.imgGithub.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Consts.GITHUB_URL)))
        }

        view.imgLinkedin.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Consts.LINKEDIN_URL)))
        }

        view.imgWebsite.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Consts.PERSONAL_WEBSITE_URL)))
        }
    }

}

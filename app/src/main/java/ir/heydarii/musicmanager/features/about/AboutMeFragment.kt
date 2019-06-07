package ir.heydarii.musicmanager.features.about


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseFragment


/**
 * A dummy fragment only to show some about me info
 */
class AboutMeFragment : BaseFragment() {

    companion object {
        fun newInstance() = AboutMeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_me, container, false)
    }

}

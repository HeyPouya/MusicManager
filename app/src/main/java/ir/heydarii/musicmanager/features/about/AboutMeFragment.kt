package ir.heydarii.musicmanager.features.about

import dagger.hilt.android.AndroidEntryPoint
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseFragment
import ir.heydarii.musicmanager.base.BaseViewModel
import ir.heydarii.musicmanager.databinding.FragmentAboutMeBinding

/**
 * Shows some about me info
 */
@AndroidEntryPoint
class AboutMeFragment : BaseFragment<FragmentAboutMeBinding, BaseViewModel>() {

    override var layout = R.layout.fragment_about_me

}

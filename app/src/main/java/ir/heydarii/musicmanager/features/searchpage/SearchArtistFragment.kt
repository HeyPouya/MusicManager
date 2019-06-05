package ir.heydarii.musicmanager.features.searchpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseFragment
import ir.heydarii.musicmanager.pojos.ArtistResponseModel
import ir.heydarii.musicmanager.repository.DataRepository
import kotlinx.android.synthetic.main.search_artist_fragment.*
import kotlinx.android.synthetic.main.search_artist_fragment.view.*
import javax.inject.Inject

class SearchArtistFragment : BaseFragment() {


    companion object {
        fun newInstance() = SearchArtistFragment()
    }

    private lateinit var viewModel: SearchArtistViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_artist_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchArtistViewModel::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.btnSearch.setOnClickListener {
            viewModel.onUserSearchedArtist(edtSearch.text.toString())
        }

        viewModel.artistResponse.observe(this, Observer {
            showRecycler(it)
        })

    }

    private fun showRecycler(artistResponseModel: ArtistResponseModel) {
    }


}

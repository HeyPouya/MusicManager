package ir.heydarii.musicmanager.features.searchpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseFragment
import ir.heydarii.musicmanager.pojos.ArtistResponseModel
import kotlinx.android.synthetic.main.search_artist_fragment.*

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

        init()
    }

    private fun init() {
        btnSearch.setOnClickListener {
            viewModel.onUserSearchedArtist(edtSearch.text.toString())
        }

        viewModel.artistResponse.observe(this, Observer {
            showRecycler(it)
        })

    }


    private fun showRecycler(artistResponseModel: ArtistResponseModel) {
        //TODO : Change this
        val adapter = SearchArtistAdapter(artistResponseModel.results.artistmatches.artist)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }


}

package ir.heydarii.musicmanager.features.searchpage

import android.content.Intent
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
import ir.heydarii.musicmanager.features.topalbums.TopAlbumsActivity
import ir.heydarii.musicmanager.pojos.ArtistResponseModel
import ir.heydarii.musicmanager.utils.Consts
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
        val adapter = SearchArtistAdapter(artistResponseModel.results.artistmatches.artist) { artistName, artistId ->
            startTopAlbumsView(artistName, artistId)
        }
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun startTopAlbumsView(artistName: String, artistId: String) {
        val intent = Intent(activity, TopAlbumsActivity::class.java)
        intent.putExtra(Consts.ARTIST_NAME, artistName)
        intent.putExtra(Consts.ARTIST_ID, artistId)
        startActivity(intent)
    }


}

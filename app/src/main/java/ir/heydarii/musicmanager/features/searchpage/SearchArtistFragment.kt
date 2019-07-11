package ir.heydarii.musicmanager.features.searchpage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseFragment
import ir.heydarii.musicmanager.features.topalbums.TopAlbumsActivity
import ir.heydarii.musicmanager.pojos.ArtistResponseModel
import ir.heydarii.musicmanager.utils.Consts
import ir.heydarii.musicmanager.utils.ViewNotifierEnums
import kotlinx.android.synthetic.main.search_artist_fragment.*

class SearchArtistFragment : BaseFragment() {

    companion object {
        fun newInstance() = SearchArtistFragment()
    }

    private lateinit var viewModel: SearchArtistViewModel
    private lateinit var adapter: SearchArtistAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_artist_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SearchArtistViewModel::class.java)
        init()  //add setOnClickListener and observe observables
        setUpRecyclerView()
        showEmptyState()
    }

    /**
     * Sets up the recycler for the first time
     */
    private fun setUpRecyclerView() {
        //TODO : Provide the adapter via dagger
        adapter = SearchArtistAdapter(emptyList()) { artistName ->
            startTopAlbumsView(artistName)
        }
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun init() {

        edtSearch.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> searchArtist()
            }
            false
        }

        btnSearch.setOnClickListener {
            searchArtist()
        }

        viewModel.getArtistResponse().observe(this, Observer {
            showRecycler(it)
        })

        viewModel.getViewNotifier().observe(this, Observer {
            when (it) {
                ViewNotifierEnums.SHOW_LOADING -> progress.visibility = View.VISIBLE
                ViewNotifierEnums.HIDE_LOADING -> progress.visibility = View.INVISIBLE
                else -> throw IllegalStateException(getString(R.string.a_notifier_is_not_defined_in_the_when_block))
            }
        })

    }

    private fun searchArtist() {
        if (edtSearch.text.toString().isNotEmpty())
            viewModel.onUserSearchedArtist(edtSearch.text.toString(), 1, Consts.API_KEY)
        else
            Toast.makeText(context, getString(R.string.please_enter_artist_name), Toast.LENGTH_LONG).show()
    }


    /**
     * SetsUp the recyclerView
     */
    private fun showRecycler(artistResponseModel: ArtistResponseModel) {
        hideEmptyState()
        adapter.list = artistResponseModel.results.artistmatches.artist
        adapter.notifyDataSetChanged()
    }

    /**
     * navigates to the view that shows the top albums of the selected artist
     */
    private fun startTopAlbumsView(artistName: String) {
        val intent = Intent(activity, TopAlbumsActivity::class.java)
        intent.putExtra(Consts.ARTIST_NAME, artistName)
        startActivity(intent)
    }

    private fun hideEmptyState() {
        empty.visibility = View.GONE
        recycler.visibility = View.VISIBLE
    }

    private fun showEmptyState() {
        empty.visibility = View.VISIBLE
        recycler.visibility = View.GONE
    }


}

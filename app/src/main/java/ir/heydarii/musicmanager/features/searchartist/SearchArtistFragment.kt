package ir.heydarii.musicmanager.features.searchartist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseFragment
import ir.heydarii.musicmanager.base.BaseViewModelFactory
import ir.heydarii.musicmanager.features.topalbums.TopAlbumsActivity
import ir.heydarii.musicmanager.pojos.Artist
import ir.heydarii.musicmanager.utils.Consts
import ir.heydarii.musicmanager.utils.ViewNotifierEnums
import kotlinx.android.synthetic.main.search_artist_fragment.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class SearchArtistFragment : BaseFragment() {
    companion object {
        fun newInstance() = SearchArtistFragment()
    }

    private lateinit var viewModel: SearchArtistViewModel
    private lateinit var adapter: SearchArtistAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_artist_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModelFactory = BaseViewModelFactory()
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SearchArtistViewModel::class.java)

        initToolbar()

        init()  //add setOnClickListener and observe observables
        setUpRecyclerView()
        showEmptyState()
    }

    private fun initToolbar() {
        imgBack.visibility = View.GONE
        txtTitle.text = getString(R.string.find_artist)
    }

    /**
     * Sets up the recycler for the first time
     */
    private fun setUpRecyclerView() {
        adapter = SearchArtistAdapter(emptyList()) { artistName ->
            startTopAlbumsView(artistName)
        }
        recycler.adapter = adapter
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recycler.layoutManager = layoutManager

        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastItem = layoutManager.findLastVisibleItemPosition()
                val total = layoutManager.itemCount
                if (total > 0)
                    if (total - 1 == lastItem)
                        viewModel.onUserSearchedArtist(
                            edtSearch.text.toString(),
                            Consts.API_KEY,
                            true
                        )
            }
        })

    }

    private fun init() {

        // enables user to search some data view the keyboard's search button
        edtSearch.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> searchArtist()
            }
            false
        }

        btnSearch.setOnClickListener {
            searchArtist()
        }

        //subscribes to viewModel to get artist response
        viewModel.getArtistResponse().observe(this, Observer {
            showRecycler(it)
        })

        //subscribes to react to loading and errors
        viewModel.getViewNotifier().observe(this, Observer {
            when (it) {
                ViewNotifierEnums.SHOW_LOADING -> progress.visibility = View.VISIBLE
                ViewNotifierEnums.HIDE_LOADING -> progress.visibility = View.INVISIBLE
                ViewNotifierEnums.ERROR_GETTING_DATA -> showTryAgain()
                else -> throw IllegalStateException(getString(R.string.a_notifier_is_not_defined_in_the_when_block))
            }
        })

    }

    /**
     * Shows try again button whenever an error accrues while receiving the albums data
     */
    private fun showTryAgain() {
        if (view != null)
            Snackbar.make(
                view!!,
                getString(R.string.please_try_again),
                Snackbar.LENGTH_LONG
            ).setAction(getString(R.string.try_again)) {
                searchArtist()
            }.show()
    }


    private fun searchArtist() {
        if (edtSearch.text.toString().isNotEmpty())
            viewModel.onUserSearchedArtist(edtSearch.text.toString(), Consts.API_KEY, false)
        else
            Toast.makeText(
                context,
                getString(R.string.please_enter_artist_name),
                Toast.LENGTH_LONG
            ).show()
    }


    /**
     * SetsUp the recyclerView
     */
    private fun showRecycler(artistResponseModel: List<Artist>) {
        hideEmptyState()
        adapter.list = artistResponseModel
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

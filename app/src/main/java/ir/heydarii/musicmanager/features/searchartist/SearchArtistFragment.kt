package ir.heydarii.musicmanager.features.searchartist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseFragment
import ir.heydarii.musicmanager.base.BaseViewModelFactory
import ir.heydarii.musicmanager.base.di.DaggerDataRepositoryComponent
import ir.heydarii.musicmanager.features.searchartist.adapter.SearchArtistAdapter
import ir.heydarii.musicmanager.features.searchartist.adapter.SearchArtistDiffCallback
import ir.heydarii.musicmanager.pojos.Artist
import ir.heydarii.musicmanager.utils.Consts
import ir.heydarii.musicmanager.utils.ViewNotifierEnums
import kotlinx.android.synthetic.main.search_artist_fragment.*
import kotlinx.android.synthetic.main.toolbar_layout.*

/**
 * User can search an Artist in this view
 */
class SearchArtistFragment : BaseFragment() {

    private lateinit var viewModel: SearchArtistViewModel
    private lateinit var adapter: SearchArtistAdapter
    private val repository = DaggerDataRepositoryComponent.create().getDataRepository()


    /**
     * inflating its layout
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_artist_fragment, container, false)
    }

    /**
     * all codes are here
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = BaseViewModelFactory(repository)

        viewModel =
                ViewModelProviders.of(activity!!, viewModelFactory).get(SearchArtistViewModel::class.java)

        initToolbar()

        init()  //add setOnClickListener and observe observables
        setUpRecyclerView()
        showEmptyState()

    }

    private fun initToolbar() {
        imgBack.visibility = View.GONE
        txtTitle.text = getString(R.string.find_artist)
    }

    private fun setUpRecyclerView() {

        adapter = SearchArtistAdapter(SearchArtistDiffCallback()) { artistName ->
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
                        viewModel.onUserSearchedArtist(edtSearch.text.toString(), Consts.API_KEY, true)
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
            showRecycler(it.toMutableList())
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

    private fun showTryAgain() {
        if (view != null)
            Snackbar.make(view!!, getString(R.string.please_try_again), Snackbar.LENGTH_LONG).setAction(getString(R.string.please_try_again)) { searchArtist() }.show()
    }


    private fun searchArtist() {
        if (edtSearch.text.toString().isNotEmpty())
            viewModel.onUserSearchedArtist(edtSearch.text.toString(), Consts.API_KEY, false)
        else
            Toast.makeText(context, getString(R.string.please_enter_artist_name), Toast.LENGTH_LONG).show()
    }

    private fun showRecycler(artistResponse: List<Artist>) {
        hideEmptyState()
        adapter.submitList(artistResponse)
    }

    private fun startTopAlbumsView(artistName: String) {
        val showTopAlbumsAction = SearchArtistFragmentDirections.showTopAlbumsAction(artistName)
        Navigation.findNavController(btnSearch).navigate(showTopAlbumsAction)
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

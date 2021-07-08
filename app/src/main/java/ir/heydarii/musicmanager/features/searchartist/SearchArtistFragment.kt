package ir.heydarii.musicmanager.features.searchartist

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseFragment
import ir.heydarii.musicmanager.databinding.FragmentSearchArtistBinding
import ir.heydarii.musicmanager.pojos.Artist
import ir.heydarii.musicmanager.utils.Constants
import ir.heydarii.musicmanager.utils.ViewNotifierEnums

/**
 * User can search an Artist in this view
 */
@AndroidEntryPoint
class SearchArtistFragment : BaseFragment<FragmentSearchArtistBinding, SearchArtistViewModel>() {

    override var layout = R.layout.fragment_search_artist
    private lateinit var adapter: SearchArtistAdapter


    /**
     * all codes are here
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setVM(viewModels())
        super.onViewCreated(view, savedInstanceState)

        init() // add setOnClickListener and observe observables
        setUpRecyclerView()
        showEmptyState()
    }

    private fun setUpRecyclerView() {

        adapter = SearchArtistAdapter(SearchArtistDiffCallback()) { artistName ->
            startTopAlbumsView(artistName)
        }

        binding.recycler.adapter = adapter
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recycler.layoutManager = layoutManager

        binding.recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastItem = layoutManager.findLastVisibleItemPosition()
                val total = layoutManager.itemCount
                if (total > 0 && total - 1 == lastItem)
                    viewModel.onUserSearchedArtist(
                        binding.edtSearch.text.toString(),
                        Constants.API_KEY,
                        true
                    )
            }
        })
    }

    private fun init() {

        // enables user to search some data view the keyboard's search button
        binding.edtSearch.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> searchArtist()
            }
            false
        }

        binding.btnSearch.setOnClickListener {
            searchArtist()
        }

        // subscribes to viewModel to get artist response
        viewModel.getArtistResponse().observe(viewLifecycleOwner, Observer {
            showRecycler(it.toMutableList())
        })

        // subscribes to react to loading and errors
        viewModel.getViewNotifier().observe(viewLifecycleOwner, Observer {
            when (it) {
                ViewNotifierEnums.SHOW_LOADING -> binding.progress.visibility = View.VISIBLE
                ViewNotifierEnums.HIDE_LOADING -> binding.progress.visibility = View.INVISIBLE
                ViewNotifierEnums.ERROR_GETTING_DATA -> showTryAgain()
                else -> throw IllegalStateException(getString(R.string.a_notifier_is_not_defined_in_the_when_block))
            }
        })
    }

    private fun showTryAgain() {
        if (view != null)
            Snackbar.make(
                requireView(),
                getString(R.string.please_try_again),
                Snackbar.LENGTH_LONG
            ).setAction(getString(R.string.please_try_again)) { searchArtist() }.show()
    }

    private fun searchArtist() {
        if (binding.edtSearch.text.toString().isNotEmpty())
            viewModel.onUserSearchedArtist(binding.edtSearch.text.toString(), Constants.API_KEY, false)
        else
            Toast.makeText(
                context,
                getString(R.string.please_enter_artist_name),
                Toast.LENGTH_LONG
            ).show()
    }

    private fun showRecycler(artistResponse: List<Artist>) {
        hideEmptyState()
        adapter.submitList(artistResponse)
    }

    private fun startTopAlbumsView(artistName: String) {
        val showTopAlbumsAction = SearchArtistFragmentDirections.showTopAlbumsAction(artistName)
       findNavController().navigate(showTopAlbumsAction)
    }

    private fun hideEmptyState() {
        binding.empty.visibility = View.GONE
        binding.recycler.visibility = View.VISIBLE
    }

    private fun showEmptyState() {
        binding.empty.visibility = View.VISIBLE
        binding.recycler.visibility = View.GONE
    }
}

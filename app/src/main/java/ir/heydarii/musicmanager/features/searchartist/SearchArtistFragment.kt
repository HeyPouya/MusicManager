package ir.heydarii.musicmanager.features.searchartist

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState.*
import dagger.hilt.android.AndroidEntryPoint
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseFragment
import ir.heydarii.musicmanager.databinding.FragmentSearchArtistBinding
import ir.heydarii.musicmanager.utils.extensions.hideKeyboard
import ir.heydarii.musicmanager.utils.extensions.onEditorImeAction
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val LAST_SEARCH_QUERY = "LAST_SEARCH_QUERY"

/**
 * User can search an artist in this view
 */
@AndroidEntryPoint
class SearchArtistFragment : BaseFragment<FragmentSearchArtistBinding, SearchArtistViewModel>() {

    override var layout = R.layout.fragment_search_artist
    private var searchJob: Job? = null
    private val adapter: SearchArtistAdapter by lazy {
        SearchArtistAdapter { artistName -> navigateToTopAlbums(artistName) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setVM(viewModels())
        super.onViewCreated(view, savedInstanceState)

        setProgressBar(binding.progress)

        initSearch()
        observeUIStatus()
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY)
        if (query != null)
            requestArtists(query)
    }

    private fun initSearch() {
        binding.recycler.adapter = adapter
        binding.edtSearch.onEditorImeAction(IME_ACTION_SEARCH) { searchArtist() }
        binding.btnSearch.setOnClickListener { searchArtist() }
    }

    private fun observeUIStatus() {
        adapter.addLoadStateListener { loadState ->
            // show empty list
            val isListEmpty = loadState.refresh is NotLoading && adapter.itemCount == 0
            showEmptyList(isListEmpty)

            // Show loading
            val isLoading = loadState.source.refresh is Loading ||
                    loadState.source.append is Loading ||
                    loadState.source.prepend is Loading
            isLoading(isLoading)

            // Show error
            val errorState = loadState.source.append as? Error
                ?: loadState.source.prepend as? Error
                ?: loadState.append as? Error
                ?: loadState.prepend as? Error
            errorState?.let {
                it.error.printStackTrace()
                showError(getString(R.string.please_try_again))
            }
        }
    }

    private fun showEmptyList(isEmpty: Boolean) {
        binding.empty.isVisible = isEmpty
        binding.recycler.isVisible = !isEmpty
    }

    private fun searchArtist() {
        if (binding.edtSearch.text.toString().isNotEmpty()) {
            binding.root.hideKeyboard()
            requestArtists(binding.edtSearch.text.toString())
        } else
            showError(getString(R.string.please_enter_artist_name))
    }

    private fun requestArtists(artistName: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.onUserSearchedArtist(artistName).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun navigateToTopAlbums(artistName: String) {
        findNavController().navigate(SearchArtistFragmentDirections.showTopAlbumsAction(artistName))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (isBindingInitialized())
            outState.putString(LAST_SEARCH_QUERY, binding.edtSearch.text.trim().toString())
    }

}

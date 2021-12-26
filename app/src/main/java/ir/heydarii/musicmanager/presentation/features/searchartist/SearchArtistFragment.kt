package ir.heydarii.musicmanager.presentation.features.searchartist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState.*
import dagger.hilt.android.AndroidEntryPoint
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.databinding.FragmentSearchArtistBinding
import ir.heydarii.musicmanager.presentation.utils.extensions.hideKeyboard
import ir.heydarii.musicmanager.presentation.utils.showError
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val LAST_SEARCH_QUERY = "LAST_SEARCH_QUERY"

/**
 * User can search an artist in this view
 */
@AndroidEntryPoint
class SearchArtistFragment : Fragment() {

    private lateinit var binding: FragmentSearchArtistBinding
    private val viewModel by viewModels<SearchArtistViewModel>()
    private var searchJob: Job? = null
    private val adapter: SearchArtistAdapter by lazy {
        SearchArtistAdapter { artistName -> navigateToTopAlbums(artistName) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchArtistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSearch()
        observeUIStatus()
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY)
        if (query != null)
            requestArtists(query)
    }

    private fun initSearch() {
        binding.recycler.adapter = adapter
        binding.edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == IME_ACTION_SEARCH) {
                searchArtist()
                return@setOnEditorActionListener true
            }
            false
        }

        binding.btnSearch.setOnClickListener { searchArtist() }
    }

    private fun observeUIStatus() {
        adapter.addLoadStateListener { loadState ->
            // show empty list
            val isListEmpty = loadState.refresh is NotLoading && adapter.itemCount == 0
            if (isListEmpty) showEmptyList() else hideEmptyList()

            // Show loading
            val isLoading = loadState.source.refresh is Loading ||
                    loadState.source.append is Loading ||
                    loadState.source.prepend is Loading

            binding.progress.isVisible = isLoading

            // Show error
            val errorState = loadState.source.append as? Error
                ?: loadState.source.prepend as? Error
                ?: loadState.append as? Error
                ?: loadState.prepend as? Error
            errorState?.let {
                it.error.printStackTrace()
                showError(getString(R.string.please_try_again), requireView())
            }
        }
    }

    private fun showEmptyList() {
        binding.empty.isVisible = true
        binding.recycler.isVisible = false
    }

    private fun hideEmptyList() {
        binding.empty.isVisible = false
        binding.recycler.isVisible = true
    }

    private fun searchArtist() {
        if (binding.edtSearch.text.toString().isNotEmpty()) {
            binding.root.hideKeyboard()
            requestArtists(binding.edtSearch.text.toString())
        } else
            showError(getString(R.string.please_enter_artist_name), requireView())
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
        outState.putString(LAST_SEARCH_QUERY, binding.edtSearch.text.trim().toString())
    }

}

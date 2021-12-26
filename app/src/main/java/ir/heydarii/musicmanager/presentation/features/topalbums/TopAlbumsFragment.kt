package ir.heydarii.musicmanager.presentation.features.topalbums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.databinding.FragmentTopAlbumsBinding
import ir.heydarii.musicmanager.presentation.utils.showError
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Shows top albums of an artist
 */
@AndroidEntryPoint
class TopAlbumsFragment : Fragment() {

    private lateinit var binding: FragmentTopAlbumsBinding
    private val viewModel by viewModels<TopAlbumsViewModel>()
    private var searchJob: Job? = null
    private val args by navArgs<TopAlbumsFragmentArgs>()
    private val adapter: TopAlbumsAdapter by lazy {
        TopAlbumsAdapter { artistName, albumName ->
            navigateToAlbumDetails(artistName, albumName)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopAlbumsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycler.adapter = adapter
        binding.txtName.text = args.artistName
        initToolbar()
        observeUIStatus()
        initPaging()
    }

    private fun initToolbar() {
        binding.toolbar.txtTitle.text = getString(R.string.top_albums)
        binding.toolbar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeUIStatus() {
        adapter.addLoadStateListener { loadState ->
            // show empty list
            val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            if (isListEmpty) showEmptyList() else hideEmptyList()

            // Show loading
            val isLoading = loadState.source.refresh is LoadState.Loading ||
                    loadState.source.append is LoadState.Loading ||
                    loadState.source.prepend is LoadState.Loading

            binding.progress.isVisible = isLoading

            // Show error
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
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

    private fun initPaging() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.requestTopAlbums(args.artistName).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun navigateToAlbumDetails(artistName: String, albumName: String) {
        val albumDetailsDir =
            TopAlbumsFragmentDirections.showAlbumDetailsActions(artistName, albumName)
        findNavController().navigate(albumDetailsDir)
    }
}

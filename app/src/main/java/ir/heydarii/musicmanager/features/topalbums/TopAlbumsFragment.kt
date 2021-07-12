package ir.heydarii.musicmanager.features.topalbums

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseFragment
import ir.heydarii.musicmanager.databinding.FragmentTopAlbumsBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Shows top albums of an artist
 */
@AndroidEntryPoint
class TopAlbumsFragment : BaseFragment<FragmentTopAlbumsBinding, TopAlbumsViewModel>() {

    override var layout = R.layout.fragment_top_albums
    private var searchJob: Job? = null
    private val args by navArgs<TopAlbumsFragmentArgs>()
    private val adapter: TopAlbumsAdapter by lazy {
        TopAlbumsAdapter { artistName, albumName ->
            navigateToAlbumDetails(artistName, albumName)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setVM(viewModels())
        super.onViewCreated(view, savedInstanceState)

        setProgressBar(binding.progress)
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
            showEmptyList(isListEmpty)

            // Show loading
            val isLoading = loadState.source.refresh is LoadState.Loading ||
                    loadState.source.append is LoadState.Loading ||
                    loadState.source.prepend is LoadState.Loading
            isLoading(isLoading)

            // Show error
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
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

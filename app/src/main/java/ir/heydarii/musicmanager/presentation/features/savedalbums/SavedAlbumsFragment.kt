package ir.heydarii.musicmanager.presentation.features.savedalbums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pouyaheydari.android.core.domain.Album
import dagger.hilt.android.AndroidEntryPoint
import ir.heydarii.musicmanager.databinding.FragmentSavedAlbumsBinding
import ir.heydarii.musicmanager.presentation.features.savedalbums.SavedAlbumsViewState.Loading
import ir.heydarii.musicmanager.presentation.features.savedalbums.SavedAlbumsViewState.Success
import ir.heydarii.musicmanager.presentation.utils.showRelevantError

/**
 * Shows albums that user has saved in the database
 */
@AndroidEntryPoint
class SavedAlbumsFragment : Fragment() {

    private lateinit var binding: FragmentSavedAlbumsBinding
    private val viewModel by viewModels<SavedAlbumsViewModel>()

    private val adapter by lazy {
        SavedAlbumsAdapter { artistName: String, albumName: String ->
            navigateToAlbumDetails(artistName, albumName)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedAlbumsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        binding.recycler.adapter = adapter
        viewModel.getAllAlbums()
    }

    private fun observeViewModel() {
        viewModel.errorLiveData.observeOnce(viewLifecycleOwner) {
            showRelevantError(it, requireView())
        }

        viewModel.getAlbumsLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is Loading -> showLoading()
                is Success -> {
                    hideLoading()
                    showAlbumsData(it.data)
                }
                is SavedAlbumsViewState.EmptyList -> {
                    hideLoading()
                    showEmptyState()
                }
            }
        }

    }

    private fun hideLoading() {
        binding.progress.isVisible = false
    }

    private fun showLoading() {
        binding.progress.isVisible = true
    }

    private fun showAlbumsData(albums: List<Album>?) {
        hideEmptyState()
        adapter.submitList(albums)
    }

    private fun showEmptyState() = with(binding) {
        empty.isVisible = true
        recycler.isVisible = false
    }

    private fun hideEmptyState() = with(binding) {
        empty.isVisible = false
        recycler.isVisible = true
    }

    private fun navigateToAlbumDetails(artistName: String, albumName: String) {
        val showDetailsViewAction =
            SavedAlbumsFragmentDirections.showAlbumDetailsActions(artistName, albumName)
        findNavController().navigate(showDetailsViewAction)
    }
}

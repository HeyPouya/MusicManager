package ir.heydarii.musicmanager.presentation.features.savedalbums

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pouyaheydari.android.core.domain.Album
import dagger.hilt.android.AndroidEntryPoint
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.framework.BaseFragment
import ir.heydarii.musicmanager.databinding.FragmentSavedAlbumsBinding
import ir.heydarii.musicmanager.presentation.features.savedalbums.SavedAlbumsViewState.Loading
import ir.heydarii.musicmanager.presentation.features.savedalbums.SavedAlbumsViewState.Success

/**
 * Shows albums that user has saved in the database
 */
@AndroidEntryPoint
class SavedAlbumsFragment : BaseFragment<FragmentSavedAlbumsBinding, SavedAlbumsViewModel>() {

    override var layout = R.layout.fragment_saved_albums
    private val adapter by lazy {
        SavedAlbumsAdapter { artistName: String, albumName: String ->
            navigateToAlbumDetails(artistName, albumName)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setVM(viewModels())
        super.onViewCreated(view, savedInstanceState)

        binding.recycler.adapter = adapter
        viewModel.getAllAlbums()

        viewModel.getAlbumsLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is Loading -> isLoading(true)
                is Success -> showAlbumsData(it.data)
                is SavedAlbumsViewState.EmptyList -> emptyState(true)
            }
        }
    }

    private fun showAlbumsData(albums: List<Album>?) {
        emptyState(false)
        adapter.submitList(albums)
    }

    private fun emptyState(isEmpty: Boolean) = with(binding) {
        empty.isVisible = isEmpty
        recycler.isVisible = !isEmpty
    }

    private fun navigateToAlbumDetails(artistName: String, albumName: String) {
        val showDetailsViewAction =
            SavedAlbumsFragmentDirections.showAlbumDetailsActions(artistName, albumName)
        findNavController().navigate(showDetailsViewAction)
    }
}

package ir.heydarii.musicmanager.features.savedalbums

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseFragment
import ir.heydarii.musicmanager.databinding.FragmentSavedAlbumsBinding
import ir.heydarii.musicmanager.pojos.savedalbums.AlbumEntity
import ir.heydarii.musicmanager.pojos.savedalbums.SavedAlbumsState.Loading
import ir.heydarii.musicmanager.pojos.savedalbums.SavedAlbumsState.Success

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
            }
        }
    }

    private fun showAlbumsData(albums: List<AlbumEntity>) {
        emptyState(albums.isEmpty())
        adapter.submitList(albums)
    }

    private fun emptyState(isEmpty: Boolean) {
        binding.empty.isVisible = isEmpty
        binding.recycler.isVisible = !isEmpty
    }

    private fun navigateToAlbumDetails(artistName: String, albumName: String) {
        val showDetailsViewAction =
            SavedAlbumsFragmentDirections.showAlbumDetailsActions(artistName, albumName, true)
        findNavController().navigate(showDetailsViewAction)
    }
}

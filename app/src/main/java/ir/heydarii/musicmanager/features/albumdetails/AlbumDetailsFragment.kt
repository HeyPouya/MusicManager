package ir.heydarii.musicmanager.features.albumdetails

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseFragment
import ir.heydarii.musicmanager.databinding.FragmentAlbumDetailsBinding
import ir.heydarii.musicmanager.pojos.albumdetails.AlbumDetailsViewState
import ir.heydarii.musicmanager.pojos.savedalbums.AlbumTracks
import ir.heydarii.musicmanager.utils.extensions.load

/**
 * Shows details of an album containing the name and tracks
 */
@AndroidEntryPoint
class AlbumDetailsFragment : BaseFragment<FragmentAlbumDetailsBinding, AlbumDetailsViewModel>() {

    private val args by navArgs<AlbumDetailsFragmentArgs>()
    override var layout = R.layout.fragment_album_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setVM(viewModels())
        super.onViewCreated(view, savedInstanceState)

        setProgressBar(binding.progressBar)
        viewModel.getAlbum(args.artistName, args.albumName, args.isOffline)
        initToolbar()
        subscribeToViewModel()

        binding.toolbar.imgSave.isVisible = true

        binding.toolbar.imgSave.setOnClickListener {
            disableSaveButton(true)
            viewModel.bookMarkClicked()
        }
    }

    private fun initToolbar() {
        binding.toolbar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun subscribeToViewModel() {
        viewModel.getAlbumsResponse().observe(viewLifecycleOwner) {
            when (it) {
                is AlbumDetailsViewState.EmptyTrackList -> emptyState(true)
                is AlbumDetailsViewState.Loading -> isLoading(true)
                is AlbumDetailsViewState.NotSaved -> showSaveAnimation(false)
                is AlbumDetailsViewState.Saved -> showSaveAnimation(true)
                is AlbumDetailsViewState.Success -> showData(it.data)
            }
        }
    }

    private fun showData(data: AlbumTracks) {
        isLoading(false)
        binding.recycler.adapter = TracksAdapter(data.tracks)
        binding.txtAlbumName.text = data.album.albumName
        binding.txtArtistName.text = data.album.artistName
        binding.imgAlbum.load(data.album.image, R.drawable.ic_album_placeholder)
    }

    private fun emptyState(isEmpty: Boolean) {
        binding.empty.isVisible = isEmpty
        binding.recycler.isVisible = !isEmpty
    }

    private fun disableSaveButton(disable: Boolean) {
        binding.toolbar.imgSave.isEnabled = !disable
    }

    private fun showSaveAnimation(saved: Boolean) {
        val resource = if (saved) R.drawable.ic_full_star else R.drawable.ic_empty_star
        binding.toolbar.imgSave.setImageResource(resource)
        disableSaveButton(false)
    }
}

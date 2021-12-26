package ir.heydarii.musicmanager.presentation.features.albumdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pouyaheydari.android.core.domain.AlbumDetails
import dagger.hilt.android.AndroidEntryPoint
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.databinding.FragmentAlbumDetailsBinding
import ir.heydarii.musicmanager.presentation.utils.extensions.load
import ir.heydarii.musicmanager.presentation.utils.showRelevantError

/**
 * Shows details of an album containing the name and tracks
 */
@AndroidEntryPoint
class AlbumDetailsFragment : Fragment() {

    private val args by navArgs<AlbumDetailsFragmentArgs>()
    private lateinit var binding: FragmentAlbumDetailsBinding
    private val viewModel by viewModels<AlbumDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToViewModel()

        viewModel.getAlbum(args.artistName, args.albumName)
        viewModel.checkBookMark(args.artistName, args.albumName)

        binding.toolbar.imgBack.setOnClickListener { findNavController().navigateUp() }
        binding.toolbar.imgSave.isVisible = true
        binding.toolbar.imgSave.setOnClickListener {
            disableSaveButton()
            viewModel.bookMarkClicked()
        }
    }

    private fun subscribeToViewModel() {
        viewModel.errorLiveData.observeOnce(viewLifecycleOwner) {
            showRelevantError(it, requireView())
        }
        viewModel.getAlbumsResponse().observe(viewLifecycleOwner) {
            when (it) {
                is AlbumDetailsViewState.EmptyTrackList -> {
                    hideLoading()
                    showEmptyState()
                }
                is AlbumDetailsViewState.Loading -> showLoading()
                is AlbumDetailsViewState.NotSaved -> {
                    showNotSavedState()
                    enableSaveButton()
                }
                is AlbumDetailsViewState.Saved -> {
                    showSavedState()
                    enableSaveButton()
                }
                is AlbumDetailsViewState.Success -> {
                    hideLoading()
                    showData(it.data)
                    hideEmptyState()
                }
            }
        }
    }

    private fun hideEmptyState() {
        binding.empty.isVisible = false
        binding.recycler.isVisible = true
    }

    private fun showEmptyState() {
        binding.empty.isVisible = true
        binding.recycler.isVisible = false
    }

    private fun hideLoading() {
        binding.progressBar.isVisible = false
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
    }

    private fun showData(data: AlbumDetails?) {
        binding.recycler.adapter = TracksAdapter(data?.tracks)
        binding.txtAlbumName.text = data?.name
        binding.txtArtistName.text = data?.artist
        binding.imgAlbum.load(data?.image.orEmpty(), R.drawable.ic_album_placeholder)
    }

    private fun disableSaveButton() {
        binding.toolbar.imgSave.isEnabled = false
    }

    private fun enableSaveButton() {
        binding.toolbar.imgSave.isEnabled = true
    }

    private fun showSavedState() {
        binding.toolbar.imgSave.setImageResource(R.drawable.ic_full_star)
    }

    private fun showNotSavedState() {
        binding.toolbar.imgSave.setImageResource(R.drawable.ic_empty_star)
    }
}

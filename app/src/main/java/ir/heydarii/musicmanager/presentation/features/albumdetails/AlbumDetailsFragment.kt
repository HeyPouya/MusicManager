package ir.heydarii.musicmanager.presentation.features.albumdetails

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pouyaheydari.android.core.domain.AlbumDetails
import dagger.hilt.android.AndroidEntryPoint
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.databinding.FragmentAlbumDetailsBinding
import ir.heydarii.musicmanager.framework.BaseFragment
import ir.heydarii.musicmanager.presentation.utils.extensions.load

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

        viewModel.getAlbum(args.artistName, args.albumName)
        viewModel.checkBookMark(args.artistName, args.albumName)

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
                is AlbumDetailsViewState.Success -> {
                    showData(it.data)
                    emptyState(false)
                }
            }
        }
    }

    private fun showData(data: AlbumDetails?) {
        isLoading(false)
        binding.recycler.adapter = TracksAdapter(data?.tracks)
        binding.txtAlbumName.text = data?.name
        binding.txtArtistName.text = data?.artist
        binding.imgAlbum.load(data?.image.orEmpty(), R.drawable.ic_album_placeholder)
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

package ir.heydarii.musicmanager.features.albumdetails

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseFragment
import ir.heydarii.musicmanager.databinding.FragmentAlbumDetailsBinding
import ir.heydarii.musicmanager.pojos.savedalbums.AlbumEntity
import ir.heydarii.musicmanager.pojos.savedalbums.TrackEntity
import ir.heydarii.musicmanager.utils.ImageStorageManager
import ir.heydarii.musicmanager.utils.ViewNotifierEnums
import ir.heydarii.musicmanager.utils.extensions.load
import javax.inject.Inject

/**
 * Shows details of an album containing the name and tracks
 */
@AndroidEntryPoint
class AlbumDetailsFragment : BaseFragment<FragmentAlbumDetailsBinding, AlbumDetailsViewModel>() {

    private val args by navArgs<AlbumDetailsFragmentArgs>()
    override var layout = R.layout.fragment_album_details
    private var albumName = ""

    @Inject
    lateinit var imageStorageManager: ImageStorageManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setVM(viewModels())
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        showData()
        subscribeToViewModel()

        // click listener for btnSave
        binding.btnSave.setOnClickListener {
            val path = saveImage()
            disableSaveButtonForASecond()
            viewModel.onClickedOnSaveButton(path)
        }
    }

    private fun initToolbar() {
        binding.toolbar.txtTitle.visibility = View.GONE
        binding.toolbar.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun subscribeToViewModel() {

        // observes the viewModel to understand the state of btnSave for the first time activity starts
        viewModel.getAlbumExistenceResponse().observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> binding.btnSave.progress = 1f
                false -> binding.btnSave.progress = 0f
            }
        })

        // subscribes to show the album data
        viewModel.getAlbumsResponse().observe(viewLifecycleOwner, Observer {
            setImagesTexts(it.album)

            showTrackList(it.tracks)
        })

        // subscribes to react to loading and errors
        viewModel.getViewNotifier().observe(viewLifecycleOwner, Observer {
            when (it) {
                ViewNotifierEnums.SHOW_LOADING -> isLoading(true)
                ViewNotifierEnums.HIDE_LOADING -> isLoading(false)
                ViewNotifierEnums.SAVED_INTO_DB -> showSaveAnimation()
                ViewNotifierEnums.REMOVED_FROM_DB -> {
                    removeImage(albumName)
                    binding.btnSave.progress = 0f
                }
                ViewNotifierEnums.EMPTY_STATE -> showEmptyState()
                ViewNotifierEnums.NOT_EMPTY -> hideEmptyState()
                ViewNotifierEnums.ERROR_GETTING_DATA -> showError(getString(R.string.please_try_again))
                ViewNotifierEnums.ERROR_DATA_NOT_AVAILABLE -> showError(getString(R.string.album_is_not_available))
                ViewNotifierEnums.ERROR_REMOVING_DATA, ViewNotifierEnums.ERROR_SAVING_DATA -> showError(
                    getString(R.string.album_not_saved)
                )

                else -> throw IllegalStateException(getString(R.string.a_notifier_is_not_defined_in_the_when_block))
            }
        })
    }

    private fun disableSaveButtonForASecond() {
        binding.btnSave.isEnabled = false
        Handler(Looper.getMainLooper()).postDelayed({
            binding.btnSave.isEnabled = true
        }, 2000)
    }

    private fun showSaveAnimation() {
        binding.btnSave.playAnimation()
    }

    private fun setImagesTexts(album: AlbumEntity) {
        binding.txtAlbumName.text = album.albumName
        binding.txtArtistName.text = album.artistName
        binding.imgAlbum.load(album.image, R.drawable.ic_album_placeholder)
    }

    private fun showData() {

        albumName = args.albumName
        viewModel.getAlbum(
            args.artistName,
            args.albumName,
            args.isOffline
        )
    }

    private fun showTrackList(tracks: List<TrackEntity>) {
        binding.recycler.adapter = TracksAdapter(tracks)
    }

    private fun hideEmptyState() {
        binding.empty.visibility = View.GONE
        binding.recycler.visibility = View.VISIBLE
    }

    private fun showEmptyState() {
        binding.empty.visibility = View.VISIBLE
        binding.recycler.visibility = View.GONE
    }

    private fun saveImage(): String {
        return imageStorageManager.saveToInternalStorage(
            requireActivity().applicationContext,
            binding.imgAlbum.drawable.toBitmap(),
            albumName
        )
    }

    private fun removeImage(path: String) {
        imageStorageManager.deleteImageFromInternalStorage(path)
    }
}

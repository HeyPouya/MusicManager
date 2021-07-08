package ir.heydarii.musicmanager.features.albumdetails

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseFragment
import ir.heydarii.musicmanager.pojos.AlbumDatabaseEntity
import ir.heydarii.musicmanager.utils.Constants
import ir.heydarii.musicmanager.utils.ImageStorageManager
import ir.heydarii.musicmanager.utils.ViewNotifierEnums
import ir.heydarii.musicmanager.utils.extensions.loadFile
import ir.heydarii.musicmanager.utils.extensions.loadUrl
import kotlinx.android.synthetic.main.album_details_main_layout.*
import kotlinx.android.synthetic.main.fragment_album_details.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.io.File
import javax.inject.Inject

/**
 * Shows details of an album containing the name and tracks
 */
@AndroidEntryPoint
class AlbumDetailsFragment : BaseFragment() {

    private var albumName = ""

    @Inject
    lateinit var imageStorageManager: ImageStorageManager

    val viewModel by viewModels<AlbumDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()

        showData()

        subscribeToViewModel()

        // click listener for btnSave
        btnSave.setOnClickListener {
            val path = saveImage()
            disableSaveButtonForASecond()
            viewModel.onClickedOnSaveButton(path)
        }
    }

    private fun initToolbar() {
        txtTitle.visibility = View.GONE
        imgBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun subscribeToViewModel() {

        // observes the viewModel to understand the state of btnSave for the first time activity starts
        viewModel.getAlbumExistenceResponse().observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> btnSave.progress = 1f
                false -> btnSave.progress = 0f
            }
        })

        // subscribes to show the album data
        viewModel.getAlbumsResponse().observe(viewLifecycleOwner, Observer {
            setImagesTexts(it)

            showTrackList(it.tracks)
        })

        // subscribes to react to loading and errors
        viewModel.getViewNotifier().observe(viewLifecycleOwner, Observer {
            when (it) {
                ViewNotifierEnums.SHOW_LOADING -> {
                    if (progress.visibility != View.VISIBLE)
                        progress.visibility = View.VISIBLE
                }
                ViewNotifierEnums.HIDE_LOADING -> {
                    if (progress.visibility == View.VISIBLE)
                        progress.visibility = View.GONE
                }
                ViewNotifierEnums.SAVED_INTO_DB -> showSaveAnimation()
                ViewNotifierEnums.REMOVED_FROM_DB -> {
                    removeImage(albumName)
                    btnSave.progress = 0f
                }
                ViewNotifierEnums.EMPTY_STATE -> showEmptyState()
                ViewNotifierEnums.NOT_EMPTY -> hideEmptyState()
                ViewNotifierEnums.ERROR_GETTING_DATA -> showTryAgain()
                ViewNotifierEnums.ERROR_DATA_NOT_AVAILABLE -> showDataNotAvailable()
                ViewNotifierEnums.ERROR_REMOVING_DATA, ViewNotifierEnums.ERROR_SAVING_DATA -> showDbError()

                else -> throw IllegalStateException(getString(R.string.a_notifier_is_not_defined_in_the_when_block))
            }
        })
    }

    private fun showDbError() {
        Snackbar.make(rootView, getString(R.string.album_not_saved), Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.please_try_again)) {
                val path = saveImage()
                viewModel.onClickedOnSaveButton(path)
            }.show()
    }

    private fun showDataNotAvailable() {
        Snackbar.make(rootView, getString(R.string.album_is_not_available), Snackbar.LENGTH_LONG)
            .show()
    }

    private fun showTryAgain() {
        Snackbar.make(rootView, getString(R.string.please_try_again), Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.please_try_again)) {
                showData()
            }.show()
    }

    private fun disableSaveButtonForASecond() {
        btnSave?.isEnabled = false
        Handler().postDelayed({
            btnSave?.isEnabled = true
        }, 2000)
    }

    private fun showSaveAnimation() {
        btnSave.playAnimation()
    }

    private fun setImagesTexts(album: AlbumDatabaseEntity) {
        if (album.image.isNotEmpty())
            if (album.image.startsWith("http"))
                imgAlbum.loadUrl(album.image, R.drawable.ic_album_placeholder)
            else {
                val file = File(album.image)
                imgAlbum.loadFile(file, R.drawable.ic_album_placeholder)
            }
        txtAlbumName.text = album.albumName
        txtArtistName.text = album.artistName
    }

    private fun showData() {

        val receivedData = arguments?.let {
            AlbumDetailsFragmentArgs.fromBundle(it)
        }

        check(receivedData != null)

        albumName = receivedData.albumName
        viewModel.getAlbum(
            receivedData.artistName,
            receivedData.albumName,
            Constants.API_KEY,
            receivedData.isOffline
        )
    }

    private fun showTrackList(tracks: List<String>) {
        recycler.adapter = TracksAdapter(tracks)
    }

    private fun hideEmptyState() {
        empty.visibility = View.GONE
        recycler.visibility = View.VISIBLE
    }

    private fun showEmptyState() {
        empty.visibility = View.VISIBLE
        recycler.visibility = View.GONE
    }

    private fun saveImage(): String {
        return imageStorageManager.saveToInternalStorage(
            requireActivity().applicationContext,
            imgAlbum.drawable.toBitmap(),
            albumName
        )
    }

    private fun removeImage(path: String) {
        imageStorageManager.deleteImageFromInternalStorage(path)
    }
}

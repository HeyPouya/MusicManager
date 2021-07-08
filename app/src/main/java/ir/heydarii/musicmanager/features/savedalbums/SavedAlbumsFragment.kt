package ir.heydarii.musicmanager.features.savedalbums

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseFragment
import ir.heydarii.musicmanager.databinding.FragmentSavedAlbumsBinding
import ir.heydarii.musicmanager.pojos.AlbumTracks
import ir.heydarii.musicmanager.utils.ViewNotifierEnums

/**
 * Shows albums that user has saved offline in the phone
 */
@AndroidEntryPoint
class SavedAlbumsFragment : BaseFragment<FragmentSavedAlbumsBinding, SavedAlbumsViewModel>() {

    override var layout = R.layout.fragment_saved_albums
    private val list = ArrayList<AlbumTracks>()
    private lateinit var adapter: SavedAlbumsAdapter

    /**
     * Codes of this fragment is here
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setVM(viewModels())
        super.onViewCreated(view, savedInstanceState)

        setUpRecycler()
    }

    private fun setUpRecycler() {
        adapter = SavedAlbumsAdapter(list) { artistName: String, albumName: String ->
            savedAlbumsClickListener(artistName, albumName)
        }
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
    }

    private fun savedAlbumsClickListener(artistName: String, albumName: String) {
        val showDetailsViewAction =
            SavedAlbumsFragmentDirections.showAlbumDetailsActions(artistName, albumName, true)
        findNavController().navigate(showDetailsViewAction)
    }

    private fun showRecycler(savedAlbums: List<AlbumTracks>) {
        list.clear()
        list.addAll(savedAlbums)
        adapter.notifyDataSetChanged()
    }

    /**
     * asks the view model to fetch data in onResume, so if the users adds some new
     * lists, he will be able to see the list when he comes back to the main view
     */
    override fun onResume() {
        super.onResume()
        viewModel.getAllAlbums()

        viewModel.getAlbumList().observe(this, Observer {
            hideEmptyState()
            showRecycler(it)
        })

        viewModel.getViewNotifier().observe(this, Observer {
            when (it) {
                ViewNotifierEnums.EMPTY_STATE -> showEmptyState()
                ViewNotifierEnums.ERROR_GETTING_DATA -> showTryAgain()
                else -> throw IllegalStateException(getString(R.string.a_notifier_is_not_defined_in_the_when_block))
            }
        })
    }

    private fun showTryAgain() {
        if (view != null)
            Snackbar.make(
                requireView(),
                getString(R.string.please_try_again),
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction(getString(R.string.please_try_again)) {
                    viewModel.getAllAlbums()
                }.show()
    }

    private fun hideEmptyState() {
        binding.empty.visibility = View.GONE
        binding.recycler.visibility = View.VISIBLE
    }

    private fun showEmptyState() {
        binding.empty.visibility = View.VISIBLE
        binding.recycler.visibility = View.GONE
    }
}

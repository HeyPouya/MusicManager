package ir.heydarii.musicmanager.features.savedalbums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseFragment
import ir.heydarii.musicmanager.base.ViewModelFactory
import ir.heydarii.musicmanager.pojos.AlbumDatabaseEntity
import ir.heydarii.musicmanager.utils.ViewNotifierEnums
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_saved_albums.*
import kotlinx.android.synthetic.main.toolbar_layout.*

/**
 * Shows albums that user has saved offline in the phone
 */
class SavedAlbumsFragment : BaseFragment() {

    private val list = ArrayList<AlbumDatabaseEntity>()
    private lateinit var viewModel: SavedAlbumsViewModel
    private lateinit var adapter: SavedAlbumsAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    /**
     * Inflates layout for this fragment
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saved_albums, container, false)
    }

    /**
     * Codes of this fragment is here
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)
                .get(SavedAlbumsViewModel::class.java)

        initToolbar()

        setUpRecycler()
    }

    private fun initToolbar() {
        imgBack.visibility = View.GONE
        txtTitle.text = getString(R.string.saved_albums)
    }

    private fun setUpRecycler() {
        adapter = SavedAlbumsAdapter(list) { artistName: String, albumName: String ->
            savedAlbumsClickListener(artistName, albumName)
        }
        recycler.adapter = adapter
        recycler.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
    }

    private fun savedAlbumsClickListener(artistName: String, albumName: String) {
        val showDetailsViewAction =
            SavedAlbumsFragmentDirections.showAlbumDetailsActions(artistName, albumName, true)
        Navigation.findNavController(rootView).navigate(showDetailsViewAction)
    }

    private fun showRecycler(savedAlbums: List<AlbumDatabaseEntity>) {
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
            Snackbar.make(view!!, getString(R.string.please_try_again), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.please_try_again)) {
                    viewModel.getAllAlbums()
                }.show()
    }

    private fun hideEmptyState() {
        empty.visibility = View.GONE
        recycler.visibility = View.VISIBLE
    }

    private fun showEmptyState() {
        empty.visibility = View.VISIBLE
        recycler.visibility = View.GONE
    }
}

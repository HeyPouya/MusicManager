package ir.heydarii.musicmanager.features.topalbums

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseFragment
import ir.heydarii.musicmanager.databinding.FragmentTopAlbumsBinding
import ir.heydarii.musicmanager.pojos.Album
import ir.heydarii.musicmanager.utils.Constants
import ir.heydarii.musicmanager.utils.ViewNotifierEnums

/**
 * Shows top albums of an artist
 */
@AndroidEntryPoint
class TopAlbumsFragment : BaseFragment<FragmentTopAlbumsBinding, TopAlbumsViewModel>() {

    override var layout = R.layout.fragment_top_albums
    private lateinit var adapter: TopAlbumsAdapter

    /**
     * All codes are here
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setVM(viewModels())
        super.onViewCreated(view, savedInstanceState)

        initToolbar()

        setUpRecycler()

        showData(savedInstanceState)

        // subscribes to get the albums data
        viewModel.getTopAlbumsLiveData().observe(viewLifecycleOwner, Observer {
            showList(it.toMutableList())
        })

        // subscribes to show or hide loading
        viewModel.getViewNotifier().observe(viewLifecycleOwner, Observer {
            when (it) {
                ViewNotifierEnums.SHOW_LOADING -> binding.progress.visibility = View.VISIBLE
                ViewNotifierEnums.HIDE_LOADING -> binding.progress.visibility = View.INVISIBLE
                ViewNotifierEnums.ERROR_GETTING_DATA -> showTryAgain()
                else -> throw java.lang.IllegalStateException(getString(R.string.a_notifier_is_not_defined_in_the_when_block))
            }
        })
    }

    private fun initToolbar() {
        binding.toolbar.txtTitle.text = getString(R.string.top_albums)

        binding.toolbar.imgBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setUpRecycler() {

        adapter = TopAlbumsAdapter(TopAlbumsDiffUtils()) { artistName, albumName ->
            showAlbumDetailsView(artistName, albumName)
        }
        binding.recycler.adapter = adapter
        val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        binding.recycler.layoutManager = layoutManager

        binding.recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastItem = layoutManager.findLastVisibleItemPosition()
                val total = layoutManager.itemCount
                if (total > 0 && total - 1 == lastItem)
                    viewModel.onTopAlbumsRequested(apiKey = Constants.API_KEY)
            }
        })
    }

    private fun showTryAgain() {
        Snackbar.make(
            binding.rootView,
            getString(R.string.please_try_again),
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(getString(R.string.please_try_again)) {
                showData(null)
            }.show()
    }

    private fun showData(savedInstanceState: Bundle?) {

        val artistName = arguments?.let {
            TopAlbumsFragmentArgs.fromBundle(it).artistName
        }

        if (!artistName.isNullOrEmpty()) {
            showArtistName(artistName)

            // don't request again to get data after rotation
            if (savedInstanceState == null)
                viewModel.onTopAlbumsRequested(artistName, Constants.API_KEY)
        } else
            throw IllegalStateException("You have to pass the artist Name")
    }

    private fun showArtistName(artistName: String) {
        binding.txtName.text = artistName
    }

    private fun showList(albumsData: List<Album>) {
        adapter.submitList(albumsData)
    }

    private fun showAlbumDetailsView(artistName: String, albumName: String) {
        val showDetailsAction =
            TopAlbumsFragmentDirections.showAlbumDetailsActions(artistName, albumName)
        findNavController().navigate(showDetailsAction)
    }
}

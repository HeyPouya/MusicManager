package ir.heydarii.musicmanager.features.topalbums

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseFragment
import ir.heydarii.musicmanager.databinding.FragmentTopAlbumsBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Shows top albums of an artist
 */
@AndroidEntryPoint
class TopAlbumsFragment : BaseFragment<FragmentTopAlbumsBinding, TopAlbumsViewModel>() {

    override var layout = R.layout.fragment_top_albums
    private var searchJob: Job? = null
    private val args by navArgs<TopAlbumsFragmentArgs>()
    private val adapter: TopAlbumsAdapter by lazy {
        TopAlbumsAdapter { artistName, albumName ->
            navigateToAlbumDetails(artistName, albumName)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setVM(viewModels())
        super.onViewCreated(view, savedInstanceState)

        binding.recycler.adapter = adapter
        binding.txtName.text = args.artistName
        initToolbar()
        initPaging()
    }

    private fun initToolbar() {
        binding.toolbar.txtTitle.text = getString(R.string.top_albums)
        binding.toolbar.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    private fun initPaging() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.requestTopAlbums(args.artistName).collectLatest {
                adapter.submitData(it)
            }
        }
    }


    private fun navigateToAlbumDetails(artistName: String, albumName: String) {
        val albumDetailsDir =
            TopAlbumsFragmentDirections.showAlbumDetailsActions(artistName, albumName)
        findNavController().navigate(albumDetailsDir)
    }
}

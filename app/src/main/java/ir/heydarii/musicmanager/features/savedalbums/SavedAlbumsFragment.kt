package ir.heydarii.musicmanager.features.savedalbums

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseFragment
import ir.heydarii.musicmanager.features.albumdetails.AlbumDetailsActivity
import ir.heydarii.musicmanager.pojos.AlbumDatabaseEntity
import ir.heydarii.musicmanager.utils.Consts
import kotlinx.android.synthetic.main.saved_albums_fragment.*

class SavedAlbumsFragment : BaseFragment() {


    /**
     * New instance function
     */
    companion object {
        fun newInstance() = SavedAlbumsFragment()
    }

    private lateinit var viewModel: SavedAlbumsViewModel
    private lateinit var adapter: SavedAlbumsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.saved_albums_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SavedAlbumsViewModel::class.java)
        setUpRecycler()
    }


    /**
     * Sets up the recycler for the first time
     */
    private fun setUpRecycler() {
        adapter = SavedAlbumsAdapter(emptyList()) { artistName: String, albumName: String ->
            savedAlbumsClickListener(
                artistName,
                albumName
            )
        }
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    /**
     * Handles the click event on items
     */
    private fun savedAlbumsClickListener(artistName: String, albumName: String) {
        val intent = Intent(context, AlbumDetailsActivity::class.java)
        intent.putExtra(Consts.IS_OFFLINE, true)
        intent.putExtra(Consts.ARTIST_NAME, artistName)
        intent.putExtra(Consts.ALBUM_NAME, albumName)
        startActivity(intent)

    }

    /**
     * Updates the recycler's list and shows it
     */
    private fun showRecycler(savedAlbums: List<AlbumDatabaseEntity>) {
        adapter.list = savedAlbums
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
            showRecycler(it)
        })
    }
}

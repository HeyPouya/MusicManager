package ir.heydarii.musicmanager.features.topalbums

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseActivity
import ir.heydarii.musicmanager.features.albumdetails.AlbumDetailsActivity
import ir.heydarii.musicmanager.pojos.ArtistTopAlbumsResponseModel
import ir.heydarii.musicmanager.utils.Consts
import ir.heydarii.musicmanager.utils.Consts.Companion.ALBUM_NAME
import ir.heydarii.musicmanager.utils.Consts.Companion.ARTIST_ID
import ir.heydarii.musicmanager.utils.Consts.Companion.ARTIST_NAME
import ir.heydarii.musicmanager.utils.ViewNotifierEnums
import kotlinx.android.synthetic.main.activity_top_albums.*

class TopAlbumsActivity : BaseActivity() {

    lateinit var viewModel: TopAlbumsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_albums)

        viewModel = ViewModelProviders.of(this).get(TopAlbumsViewModel::class.java)

        showData(savedInstanceState)

        //subscribes to get the albums data
        viewModel.getTopAlbumsLiveData().observe(this, Observer {
            showList(it)
        })

        //subscribes to show or hide loading
        viewModel.getViewNotifier().observe(this, Observer {
            when (it) {
                ViewNotifierEnums.SHOW_LOADING -> progress.visibility = View.VISIBLE
                ViewNotifierEnums.HIDE_LOADING -> progress.visibility = View.INVISIBLE
            }
        })
    }

    /**
     * Checks if the developer has passed data via intent or not
     * and requests ViewModel to fetch the data
     */
    private fun showData(savedInstanceState: Bundle?) {

        val artistName = intent.getStringExtra(ARTIST_NAME)
        val artistId = intent.getStringExtra(ARTIST_ID)

        if (!artistName.isNullOrEmpty() && !artistId.isNullOrEmpty()) {
            showArtistName(artistName)

            //don't request again to get data after rotation
            if (savedInstanceState == null)
                viewModel.onTopAlbumsRequested(artistName, 1, Consts.API_KEY)
        } else
            throw IllegalStateException("You have to pass the artist Name and the Artist Id")

    }

    /**
     * Displays the artistName on top of the page
     */
    private fun showArtistName(artistName: String) {
        txtName.text = artistName
    }

    /**
     * Displays the topAlbums
     */
    private fun showList(albumsViewModel: ArtistTopAlbumsResponseModel) {
        recycler.adapter = TopAlbumsAdapter(albumsViewModel.topalbums.album) { artistName, albumeName ->
            showAlbumDetailsView(artistName, albumeName)
        }
        recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    }

    private fun showAlbumDetailsView(artistName: String, albumeName: String) {
        val intent = Intent(this, AlbumDetailsActivity::class.java)
        intent.putExtra(ARTIST_NAME, artistName)
        intent.putExtra(ALBUM_NAME, albumeName)
        startActivity(intent)
    }

}

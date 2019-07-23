package ir.heydarii.musicmanager.features.topalbums

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseActivity
import ir.heydarii.musicmanager.features.albumdetails.AlbumDetailsActivity
import ir.heydarii.musicmanager.pojos.Album
import ir.heydarii.musicmanager.utils.Consts
import ir.heydarii.musicmanager.utils.Consts.Companion.ALBUM_NAME
import ir.heydarii.musicmanager.utils.Consts.Companion.ARTIST_NAME
import ir.heydarii.musicmanager.utils.ViewNotifierEnums
import kotlinx.android.synthetic.main.activity_top_albums.*

class TopAlbumsActivity : BaseActivity() {

    lateinit var viewModel: TopAlbumsViewModel
    private lateinit var adapter: TopAlbumsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_albums)

        viewModel = ViewModelProviders.of(this).get(TopAlbumsViewModel::class.java)

        setUpRecycler()

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
                ViewNotifierEnums.ERROR_GETTING_DATA -> showTryAgain()
                else -> throw java.lang.IllegalStateException(getString(R.string.a_notifier_is_not_defined_in_the_when_block))

            }
        })
    }

    private fun setUpRecycler() {

        adapter = TopAlbumsAdapter(emptyList()) { artistName, albumName ->
            showAlbumDetailsView(artistName, albumName)
        }
        recycler.adapter = adapter
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler.layoutManager = layoutManager

        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastItem = layoutManager.findLastVisibleItemPosition()
                val total = layoutManager.itemCount
                if (total > 0)
                    if (total - 1 == lastItem)
                        viewModel.onTopAlbumsRequested(apiKey = Consts.API_KEY)
            }
        })
    }

    /**
     * Shows try again button whenever an error accrues while receiving the top albums data
     */
    private fun showTryAgain() {
        val parentLayout = findViewById<View>(android.R.id.content)
        Snackbar.make(parentLayout, getString(R.string.please_try_again), Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.try_again)) {
                showData(null)
            }.show()
    }

    /**
     * Checks if the developer has passed data via intent or not
     * and requests ViewModel to fetch the data
     */
    private fun showData(savedInstanceState: Bundle?) {

        val artistName = intent.getStringExtra(ARTIST_NAME)

        if (!artistName.isNullOrEmpty()) {
            showArtistName(artistName)

            //don't request again to get data after rotation
            if (savedInstanceState == null)
                viewModel.onTopAlbumsRequested(artistName, Consts.API_KEY)
        } else
            throw IllegalStateException("You have to pass the artist Name")

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
    private fun showList(albumsViewModel: List<Album>) {
        adapter.list = albumsViewModel
        adapter.notifyDataSetChanged()
    }

    /**
     * Navigates to  album details view
     */
    private fun showAlbumDetailsView(artistName: String, albumeName: String) {
        val intent = Intent(this, AlbumDetailsActivity::class.java)
        intent.putExtra(ARTIST_NAME, artistName)
        intent.putExtra(ALBUM_NAME, albumeName)
        startActivity(intent)
    }

}

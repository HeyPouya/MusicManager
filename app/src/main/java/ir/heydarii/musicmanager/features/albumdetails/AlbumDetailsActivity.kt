package ir.heydarii.musicmanager.features.albumdetails

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseActivity
import ir.heydarii.musicmanager.pojos.AlbumDatabaseEntity
import ir.heydarii.musicmanager.utils.Consts
import ir.heydarii.musicmanager.utils.Consts.Companion.IS_OFFLINE
import ir.heydarii.musicmanager.utils.ViewNotifierEnums
import kotlinx.android.synthetic.main.activity_album_details.*
import kotlinx.android.synthetic.main.album_details_main_layout.*

class AlbumDetailsActivity : BaseActivity() {

    lateinit var viewModel: AlbumDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_details)

        viewModel = ViewModelProviders.of(this).get(AlbumDetailsViewModel::class.java)

        showData()

        //subscribes to show the album data
        viewModel.getAlbumsResponse().observe(this, Observer {
            setImagesTexts(it)

            showTrackList(it.tracks)

        })

        btnSave.setOnClickListener {
            saveAlbum()
        }

        //subscribes to show or hide loading
        viewModel.getViewNotifier().observe(this, Observer {

            when (it) {
                ViewNotifierEnums.SHOW_LOADING -> progress.visibility = View.VISIBLE
                ViewNotifierEnums.HIDE_LOADING-> {
                    progress.visibility = View.INVISIBLE
                    switcher.showPrevious()
                }
            }
        })

    }

    private fun saveAlbum() {
        viewModel.saveAlbum()
    }

    /**
     * Sets Albums general data in the view
     */
    private fun setImagesTexts(album: AlbumDatabaseEntity) {
        Picasso.get().load(album.image).into(imgAlbum)
        txtAlbumName.text = album.albumName
        txtArtistName.text = album.artistName
    }

    /**
     * Gets intents and asks viewModel to fetch the data
     */
    private fun showData() {
        val isOffline = intent.getBooleanExtra(IS_OFFLINE, false)
        val artistName = intent.getStringExtra(Consts.ARTIST_NAME)
        val albumName = intent.getStringExtra(Consts.ALBUM_NAME)

        viewModel.getAlbum(artistName, albumName, Consts.API_KEY, isOffline)

    }


    /**
     * Shows tracks in a recycler view
     */
    private fun showTrackList(tracks: List<String>) {
        recycler.adapter = TracksAdapter(tracks)
        recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }
}

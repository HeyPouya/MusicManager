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
import ir.heydarii.musicmanager.pojos.Track
import ir.heydarii.musicmanager.utils.Consts
import kotlinx.android.synthetic.main.activity_album_details.*

class AlbumDetailsActivity : BaseActivity() {

    lateinit var viewModel: AlbumDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_details)

        viewModel = ViewModelProviders.of(this).get(AlbumDetailsViewModel::class.java)


        val artistName = intent.getStringExtra(Consts.ARTIST_NAME)
        val albumName = intent.getStringExtra(Consts.ALBUM_NAME)


        txtToolbarTitle.title = albumName


        viewModel.getAlbumsResponse().observe(this, Observer {

            Picasso.get().load(it.album.image.last().text).into(imgAlbum)
            txtAlbumeName.text = "Album : ${it.album.name}"
            txtArtistName.text = "Artist : ${it.album.artist}"


            showTrackList(it.album.tracks.track)
        })

        viewModel.getLoadingData().observe(this, Observer {

            progress.visibility = if (it == Consts.SHOW_LOADING) View.VISIBLE else { switcher.showPrevious()
                View.INVISIBLE
            }
        })

        viewModel.getAlbum(artistName, albumName, Consts.API_KEY)


    }

    private fun showTrackList(tracks: List<Track>) {
        recycler.adapter = TracksAdapter(tracks)
        recycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }
}

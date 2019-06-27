package ir.heydarii.musicmanager.features.albumdetails

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.orhanobut.logger.Logger
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseActivity
import ir.heydarii.musicmanager.utils.AppDatabase
import ir.heydarii.musicmanager.utils.Consts
import ir.heydarii.musicmanager.utils.Consts.Companion.ALBUM_DB_NAME
import ir.heydarii.musicmanager.utils.Consts.Companion.IS_OFFLINE
import kotlinx.android.synthetic.main.activity_album_details.*

class AlbumDetailsActivity : BaseActivity() {

    lateinit var viewModel: AlbumDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_details)

        viewModel = ViewModelProviders.of(this).get(AlbumDetailsViewModel::class.java)


        val isOffline = intent.getBooleanExtra(IS_OFFLINE, false)
        val artistName = intent.getStringExtra(Consts.ARTIST_NAME)
        val albumName = intent.getStringExtra(Consts.ALBUM_NAME)


        viewModel.getAlbumsResponse().observe(this, Observer {

            Picasso.get().load(it.image).into(imgAlbum)
            txtAlbumName.text = it.albumName
            txtArtistName.text = it.artistName

            showTrackList(it.tracks)


            val db = Room.databaseBuilder(this, AppDatabase::class.java, ALBUM_DB_NAME)
                .build()


            db.albumsDAO().saveAlbum(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {
                    Logger.d(it)
                })

        })

        viewModel.getLoadingData().observe(this, Observer {

            progress.visibility = if (it == Consts.SHOW_LOADING) View.VISIBLE else {
                switcher.showPrevious()
                View.INVISIBLE
            }
        })

        viewModel.getAlbum(artistName, albumName, Consts.API_KEY,isOffline)


    }


    /**
     * Shows tracks in a recycler view
     */
    private fun showTrackList(tracks: List<String>) {
        recycler.adapter = TracksAdapter(tracks)
        recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }
}

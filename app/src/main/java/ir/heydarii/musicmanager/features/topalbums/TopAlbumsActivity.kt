package ir.heydarii.musicmanager.features.topalbums

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.base.BaseActivity
import ir.heydarii.musicmanager.pojos.ArtistTopAlbumsResponseModel
import ir.heydarii.musicmanager.utils.Consts
import ir.heydarii.musicmanager.utils.Consts.Companion.ARTIST_ID
import ir.heydarii.musicmanager.utils.Consts.Companion.ARTIST_NAME
import kotlinx.android.synthetic.main.activity_top_albums.*

class TopAlbumsActivity : BaseActivity() {

    lateinit var viewModel: TopAlbumsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_albums)

        viewModel = ViewModelProviders.of(this).get(TopAlbumsViewModel::class.java)

        val artistName = intent.getStringExtra(ARTIST_NAME)
        val artistId = intent.getStringExtra(ARTIST_ID)

        if (!artistName.isNullOrEmpty() && !artistId.isNullOrEmpty()) {

            showArtistName(artistName)
            viewModel.topAlbumsData.observe(this, Observer {
                showList(it)
            })


            if (savedInstanceState == null)
                viewModel.onTopAlbumsRequested(artistName, 1, Consts.API_KEY)


        }


    }

    private fun showArtistName(artistName: String) {
        txtName.text = artistName
    }

    private fun showList(albumsViewModel: ArtistTopAlbumsResponseModel) {

        recycler.adapter = TopAlbumsAdapter(albumsViewModel.topalbums.album)
        recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    }

}

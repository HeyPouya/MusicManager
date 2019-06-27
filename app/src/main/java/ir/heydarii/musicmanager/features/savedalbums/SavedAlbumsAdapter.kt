package ir.heydarii.musicmanager.features.savedalbums

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import com.squareup.picasso.Picasso
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.pojos.AlbumDatabaseEntity
import kotlinx.android.synthetic.main.search_layout_item.view.*

class SavedAlbumsAdapter(var list: List<AlbumDatabaseEntity>) : RecyclerView.Adapter<SavedAlbumsAdapter.SearchArtistViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchArtistViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_layout_item, parent, false)
        return SearchArtistViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: SearchArtistViewHolder, position: Int) {
        holder.bind(list[position])
    }


    class SearchArtistViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(album: AlbumDatabaseEntity) {
            view.txtName.text = album.albumName

            Logger.d(album.albumName)
            // Last image has always the best quality
            if (!album.image.isNullOrEmpty())
                Picasso.get().load(album.image).into(view.imgArtist)


        }


    }
}
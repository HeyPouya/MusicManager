package ir.heydarii.musicmanager.features.topalbums

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.pojos.Album
import kotlinx.android.synthetic.main.search_layout_item.view.*

class TopAlbumsAdapter(private val list: List<Album>) : RecyclerView.Adapter<TopAlbumsAdapter.SearchArtistViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchArtistViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_layout_item, parent, false)
        return SearchArtistViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: SearchArtistViewHolder, position: Int) {
        holder.bind(list[position])
    }


    class SearchArtistViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(album: Album) {
            view.txtName.text = album.name
            if (album.image.last().text.isNotEmpty())
                Picasso.get().load(album.image.last().text).into(view.imgArtist)
        }


    }
}
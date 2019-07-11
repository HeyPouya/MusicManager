package ir.heydarii.musicmanager.features.topalbums

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.pojos.Album
import kotlinx.android.synthetic.main.search_layout_item.view.*

class TopAlbumsAdapter(private val list: List<Album>, private val clickListener: (String, String) -> Unit) :
        RecyclerView.Adapter<TopAlbumsAdapter.SearchArtistViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchArtistViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_layout_item, parent, false)
        return SearchArtistViewHolder(view, clickListener)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: SearchArtistViewHolder, position: Int) {
        holder.bind(list[position])
    }


    class SearchArtistViewHolder(private val view: View, val clickListener: (String, String) -> Unit) :
            RecyclerView.ViewHolder(view) {
        fun bind(album: Album) {
            view.txtName.text = album.name

            // Last image has always the best quality
            if (album.image.last().text.isNotEmpty())
                Picasso.get().load(album.image.last().text).placeholder(R.drawable.ic_album_placeholder).into(view.imgArtist)

            view.setOnClickListener {
                clickListener(album.artist.name, album.name)
            }
        }


    }
}
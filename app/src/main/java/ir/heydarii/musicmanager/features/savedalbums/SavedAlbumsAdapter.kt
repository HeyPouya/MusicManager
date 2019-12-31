package ir.heydarii.musicmanager.features.savedalbums

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.pojos.AlbumDatabaseEntity
import java.io.File
import kotlinx.android.synthetic.main.search_layout_item.view.*

/**
 * Adapter for recycler view to show saved albums
 */
class SavedAlbumsAdapter(private var list: List<AlbumDatabaseEntity>, private var clickListener: (String, String) -> Unit) :
        RecyclerView.Adapter<SavedAlbumsAdapter.SearchArtistViewHolder>() {

    /**
     * Inflates views for items in recycler view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchArtistViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.saved_layout_item, parent, false)
        return SearchArtistViewHolder(view, clickListener)
    }

    /**
     * returns list size
     */
    override fun getItemCount() = list.size

    /**
     * Passes the object to bind method of ViewHolder
     */
    override fun onBindViewHolder(holder: SearchArtistViewHolder, position: Int) {
        holder.bind(list[position])
    }

    /**
     * ViewHolder for saved items
     */
    class SearchArtistViewHolder(private val view: View, var clickListener: (String, String) -> Unit) :
            RecyclerView.ViewHolder(view) {

        /**
         * Sets texts and click listeners for items
         */
        fun bind(album: AlbumDatabaseEntity) {
            view.txtName.text = album.albumName

            // Last image has always the best quality
            if (!album.image.isNullOrEmpty()) {
                val file = File(album.image)
                if (file.exists())
                    Picasso.get().load(file).placeholder(R.drawable.ic_album_placeholder).into(view.imgArtist)
            }

            view.setOnClickListener {
                clickListener(album.artistName, album.albumName)
            }
        }
    }
}

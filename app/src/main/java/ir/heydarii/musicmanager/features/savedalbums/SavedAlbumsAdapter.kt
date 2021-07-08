package ir.heydarii.musicmanager.features.savedalbums

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.pojos.AlbumTracks
import ir.heydarii.musicmanager.utils.extensions.loadFile
import kotlinx.android.synthetic.main.search_layout_item.view.*
import java.io.File

/**
 * Adapter for recycler view to show saved albums
 */
class SavedAlbumsAdapter(
    private var list: ArrayList<AlbumTracks>,
    private var clickListener: (String, String) -> Unit
) :
    RecyclerView.Adapter<SavedAlbumsAdapter.SearchArtistViewHolder>() {

    /**
     * Inflates views for items in recycler view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchArtistViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.saved_layout_item, parent, false)
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
    class SearchArtistViewHolder(
        private val view: View,
        var clickListener: (String, String) -> Unit
    ) :
        RecyclerView.ViewHolder(view) {

        /**
         * Sets texts and click listeners for items
         */
        fun bind(album: AlbumTracks) {
            view.txtName.text = album.album.albumName

            // Last image has always the best quality
            if (!album.album.image.isNullOrEmpty()) {
                val file = File(album.album.image)
                if (file.exists())
                    view.imgArtist.loadFile(file, R.drawable.ic_album_placeholder)
            }

            view.setOnClickListener {
                clickListener(album.album.artistName, album.album.albumName)
            }
        }
    }
}

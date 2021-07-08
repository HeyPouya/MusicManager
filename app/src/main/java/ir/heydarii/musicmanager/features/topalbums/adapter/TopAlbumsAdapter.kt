package ir.heydarii.musicmanager.features.topalbums.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.pojos.Album
import ir.heydarii.musicmanager.utils.extensions.loadUrl
import kotlinx.android.synthetic.main.search_layout_item.view.*

/**
 * Adapter for TopAlbums of an Artist view
 */
class TopAlbumsAdapter(
    topAlbumsDiffUtils: TopAlbumsDiffUtils,
    private val clickListener: (String, String) -> Unit
) :
    ListAdapter<Album, TopAlbumsAdapter.SearchArtistViewHolder>(topAlbumsDiffUtils) {

    /**
     * Inflates layout for the recycler view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchArtistViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_layout_item, parent, false)
        return SearchArtistViewHolder(view, clickListener)
    }

    /**
     * sends object to the bind method of ViewHolder
     */
    override fun onBindViewHolder(holder: SearchArtistViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * ViewHolder for recycler view
     */
    class SearchArtistViewHolder(
        private val view: View,
        val clickListener: (String, String) -> Unit
    ) :
        RecyclerView.ViewHolder(view) {

        /**
         * sets texts and click listener for items
         */
        fun bind(album: Album) {
            view.txtName.text = album.name

            // Last image has always the best quality
            if (album.image.last().text.isNotEmpty())
                view.imgArtist.loadUrl(album.image.last().text, R.drawable.ic_album_placeholder)

            view.setOnClickListener {
                clickListener(album.artist.name, album.name)
            }
        }
    }
}

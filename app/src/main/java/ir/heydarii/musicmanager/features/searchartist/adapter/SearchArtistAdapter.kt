package ir.heydarii.musicmanager.features.searchartist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.pojos.Artist
import ir.heydarii.musicmanager.utils.extensions.loadUrl
import kotlinx.android.synthetic.main.search_layout_item.view.*

/**
 * shows artist in search artist view
 */
class SearchArtistAdapter(
    searchArtistDiffCallback: SearchArtistDiffCallback,
    private val clickListener: (String) -> Unit
) :
    ListAdapter<Artist, SearchArtistAdapter.SearchArtistViewHolder>(searchArtistDiffCallback) {

    /**
     * inflates the layout for the recyclerView
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchArtistViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_layout_item, parent, false)
        return SearchArtistViewHolder(view, clickListener)
    }

    /**
     * sends object to bind method in ViewHolder
     */
    override fun onBindViewHolder(holder: SearchArtistViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * ViewHolder for recyclerView
     */
    class SearchArtistViewHolder(private val view: View, val clickListener: (String) -> Unit) :
        RecyclerView.ViewHolder(view) {

        /**
         * Sets texts and clickListener for the items
         */
        fun bind(artist: Artist) {
            view.txtName.text = artist.name

            // Last image has always the best quality
            if (artist.image.last().text.isNotEmpty())
                view.imgArtist.loadUrl(artist.image.last().text, R.drawable.ic_album_placeholder)

            view.setOnClickListener {
                clickListener(artist.name)
            }
        }
    }
}

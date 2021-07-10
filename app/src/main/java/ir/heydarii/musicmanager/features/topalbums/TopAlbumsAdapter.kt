package ir.heydarii.musicmanager.features.topalbums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.databinding.SearchLayoutItemBinding
import ir.heydarii.musicmanager.pojos.Album
import ir.heydarii.musicmanager.utils.extensions.loadUrl

/**
 * Adapter for TopAlbums of an Artist view
 */
class TopAlbumsAdapter(private val clickListener: (String, String) -> Unit) :
    PagingDataAdapter<Album, TopAlbumsAdapter.SearchArtistViewHolder>(TopAlbumsDiffUtils()) {

    /**
     * Inflates layout for the recycler view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchArtistViewHolder {

        val binding = DataBindingUtil.inflate<SearchLayoutItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.search_layout_item,
            parent,
            false
        )
        return SearchArtistViewHolder(binding)
    }

    /**
     * sends object to the bind method of ViewHolder
     */
    override fun onBindViewHolder(holder: SearchArtistViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    /**
     * ViewHolder for recycler view
     */
    inner class SearchArtistViewHolder(private val binding: SearchLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * sets texts and click listener for items
         */
        fun bind(album: Album) = with(binding) {
            txtName.text = album.name

            // Last image has always the best quality
            if (album.image.last().text.isNotEmpty())
                imgArtist.loadUrl(album.image.last().text, R.drawable.ic_album_placeholder)

            root.setOnClickListener { clickListener(album.artist.name, album.name) }
        }
    }
}

/**
 * DiffUtils for TopAlbums
 */
class TopAlbumsDiffUtils : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album) =
        oldItem.name == newItem.name && oldItem.mbid == newItem.mbid

    override fun areContentsTheSame(oldItem: Album, newItem: Album) =
        oldItem.name == newItem.name && oldItem.image == newItem.image
}


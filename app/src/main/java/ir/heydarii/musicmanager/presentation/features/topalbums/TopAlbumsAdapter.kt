package ir.heydarii.musicmanager.presentation.features.topalbums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pouyaheydari.android.core.domain.Album
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.databinding.SearchLayoutItemBinding
import ir.heydarii.musicmanager.presentation.utils.extensions.load

/**
 * Adapter for TopAlbums of an Artist view
 */
class TopAlbumsAdapter(private val clickListener: (String, String) -> Unit) :
    PagingDataAdapter<Album, TopAlbumsAdapter.SearchArtistViewHolder>(TopAlbumsDiffUtils()) {

    /**
     * Inflates layout for the recycler view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchArtistViewHolder {

        val binding = SearchLayoutItemBinding.inflate(
            LayoutInflater.from(parent.context),
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
            txtName.text = album.albumName
            imgArtist.load(album.image, R.drawable.ic_album_placeholder)
            root.setOnClickListener { clickListener(album.artistName, album.albumName) }
        }
    }
}

/**
 * DiffUtils for TopAlbums
 */
class TopAlbumsDiffUtils : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album) =
        oldItem.albumName == newItem.albumName && oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Album, newItem: Album) =
        oldItem.albumName == newItem.albumName && oldItem.image == newItem.image
}


package ir.heydarii.musicmanager.features.searchartist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.databinding.SearchLayoutItemBinding
import ir.heydarii.musicmanager.pojos.Artist
import ir.heydarii.musicmanager.utils.extensions.loadUrl

/**
 * shows artist in search artist view
 */
class SearchArtistAdapter(private val clickListener: (String) -> Unit) :
    PagingDataAdapter<Artist, SearchArtistAdapter.SearchArtistViewHolder>(SearchArtistDiffCallback()) {

    /**
     * inflates the layout for the recyclerView
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
     * sends object to bind method in ViewHolder
     */
    override fun onBindViewHolder(holder: SearchArtistViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    /**
     * ViewHolder for recyclerView
     */
    inner class SearchArtistViewHolder(private val binding: SearchLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Sets texts and clickListener for the items
         */
        fun bind(artist: Artist) = with(binding) {
            txtName.text = artist.name

            // Last image has always the best quality
            if (artist.image.last().text.isNotEmpty())
                imgArtist.loadUrl(artist.image.last().text, R.drawable.ic_album_placeholder)

            root.setOnClickListener {
                clickListener(artist.name)
            }
        }
    }
}

/**
 * DiffUtils for Artists
 */
class SearchArtistDiffCallback : DiffUtil.ItemCallback<Artist>() {

    override fun areItemsTheSame(oldItem: Artist, newItem: Artist) =
        oldItem.name == newItem.name && oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: Artist, newItem: Artist) =
        oldItem.name == newItem.name && oldItem.url == newItem.url
}


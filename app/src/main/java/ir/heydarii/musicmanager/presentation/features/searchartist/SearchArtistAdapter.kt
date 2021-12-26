package ir.heydarii.musicmanager.presentation.features.searchartist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pouyaheydari.android.core.domain.Artist
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.databinding.SearchLayoutItemBinding
import ir.heydarii.musicmanager.presentation.utils.extensions.load

/**
 * shows artist in search artist view
 */
class SearchArtistAdapter(private val clickListener: (String) -> Unit) :
    PagingDataAdapter<Artist, SearchArtistAdapter.SearchArtistViewHolder>(SearchArtistDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchArtistViewHolder {
        val binding = SearchLayoutItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchArtistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchArtistViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class SearchArtistViewHolder(private val binding: SearchLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Sets texts and clickListener for the items
         */
        fun bind(artist: Artist) = with(binding) {
            txtName.text = artist.name
            imgArtist.load(artist.image, R.drawable.ic_album_placeholder)
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
        oldItem.name == newItem.name && oldItem.image == newItem.image

    override fun areContentsTheSame(oldItem: Artist, newItem: Artist) =
        oldItem.name == newItem.name && oldItem.image == newItem.image
}


package ir.heydarii.musicmanager.presentation.features.savedalbums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pouyaheydari.android.core.domain.Album
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.databinding.SavedAlbumsItemBinding
import ir.heydarii.musicmanager.presentation.utils.extensions.load

/**
 * Adapter to show saved albums in [SavedAlbumsFragment]
 */
class SavedAlbumsAdapter(private var clickListener: (String, String) -> Unit) :
    ListAdapter<Album, SavedAlbumsAdapter.SearchArtistViewHolder>(AlbumDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchArtistViewHolder {
        val binding = SavedAlbumsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchArtistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchArtistViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SearchArtistViewHolder(private val binding: SavedAlbumsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds albums data to the recycler view items
         */
        fun bind(album: Album) = with(binding) {
            txtName.text = album.albumName
            imgArtist.load(album.image, R.drawable.ic_album_placeholder)
            root.setOnClickListener {
                clickListener(album.artistName, album.albumName)
            }
        }
    }
}

/**
 * DiffUtil class for [SavedAlbumsAdapter]
 */
class AlbumDiffUtils : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Album, newItem: Album) =
        oldItem.albumName == newItem.albumName &&
                oldItem.artistName == newItem.artistName &&
                oldItem.image == newItem.image
}

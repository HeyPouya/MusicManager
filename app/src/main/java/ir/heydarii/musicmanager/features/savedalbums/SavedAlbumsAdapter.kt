package ir.heydarii.musicmanager.features.savedalbums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.databinding.SavedAlbumsItemBinding
import ir.heydarii.musicmanager.pojos.savedalbums.AlbumEntity
import ir.heydarii.musicmanager.utils.extensions.load

/**
 * Adapter to show saved albums in [SavedAlbumsFragment]
 */
class SavedAlbumsAdapter(private var clickListener: (String, String) -> Unit) :
    ListAdapter<AlbumEntity, SavedAlbumsAdapter.SearchArtistViewHolder>(AlbumDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchArtistViewHolder {
        val binding = DataBindingUtil.inflate<SavedAlbumsItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.saved_albums_item,
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
        fun bind(album: AlbumEntity) = with(binding) {
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
class AlbumDiffUtils : DiffUtil.ItemCallback<AlbumEntity>() {
    override fun areItemsTheSame(oldItem: AlbumEntity, newItem: AlbumEntity) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: AlbumEntity, newItem: AlbumEntity) =
        oldItem.albumName == newItem.albumName &&
                oldItem.artistName == newItem.artistName &&
                oldItem.image == newItem.image
}

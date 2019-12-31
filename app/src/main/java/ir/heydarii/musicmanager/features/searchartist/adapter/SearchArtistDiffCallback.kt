package ir.heydarii.musicmanager.features.searchartist.adapter

import androidx.recyclerview.widget.DiffUtil
import ir.heydarii.musicmanager.pojos.Artist

/**
 * DiffUtils for Artists
 */
class SearchArtistDiffCallback : DiffUtil.ItemCallback<Artist>() {

    override fun areItemsTheSame(oldItem: Artist, newItem: Artist) =
            oldItem.name == newItem.name && oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: Artist, newItem: Artist) =
            oldItem.name == newItem.name && oldItem.url == newItem.url
}

package ir.heydarii.musicmanager.features.topalbums.adapter

import androidx.recyclerview.widget.DiffUtil
import ir.heydarii.musicmanager.pojos.Album

/**
 * DiffUtils for TopAlbums
 */
class TopAlbumsDiffUtils : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album) = oldItem.name == newItem.name && oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: Album, newItem: Album) = oldItem.name == newItem.name && oldItem.url == newItem.url
}
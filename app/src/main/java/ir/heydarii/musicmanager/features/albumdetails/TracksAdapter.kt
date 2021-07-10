package ir.heydarii.musicmanager.features.albumdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.databinding.TracksLayoutItemBinding
import ir.heydarii.musicmanager.pojos.savedalbums.TrackEntity

/**
 * Adapter to show tracks of songs
 */
class TracksAdapter(val list: List<TrackEntity>) :
    RecyclerView.Adapter<TracksAdapter.TracksViewHolder>() {

    /**
     * inflates views for recycler view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {

        val binding = DataBindingUtil.inflate<TracksLayoutItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.tracks_layout_item,
            parent,
            false
        )
        return TracksViewHolder(binding)
    }

    /**
     * Returns item size
     */
    override fun getItemCount() = list.size

    /**
     * Sends the object to bind method in ViewHolder
     */
    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    /**
     * ViewHolder class
     */
    class TracksViewHolder(private val binding: TracksLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Sets texts in views
         */
        fun bind(track: TrackEntity, position: Int) = with(binding) {
            txtTrackNumber.text = position.toString()
            txtTrackName.text = track.name
        }
    }
}

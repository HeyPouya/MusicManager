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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        val binding = DataBindingUtil.inflate<TracksLayoutItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.tracks_layout_item,
            parent,
            false
        )
        return TracksViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    /**
     * [RecyclerView.ViewHolder] of the [TracksAdapter]
     */
    class TracksViewHolder(private val binding: TracksLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * binds tracks to the recycler view items
         */
        fun bind(track: TrackEntity, position: Int) = with(binding) {
            txtTrackNumber.text = position.toString()
            txtTrackName.text = track.name
        }
    }
}

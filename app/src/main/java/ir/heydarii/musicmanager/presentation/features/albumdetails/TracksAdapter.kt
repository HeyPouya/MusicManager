package ir.heydarii.musicmanager.presentation.features.albumdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.pouyaheydari.android.core.domain.Track
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.databinding.TracksLayoutItemBinding

/**
 * Adapter to show tracks of songs
 */
class TracksAdapter(val list: List<Track>?) :
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

    override fun getItemCount() = list?.size ?: 0

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        holder.bind(list?.get(position), position)
    }

    /**
     * [RecyclerView.ViewHolder] of the [TracksAdapter]
     */
    class TracksViewHolder(private val binding: TracksLayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * binds tracks to the recycler view items
         */
        fun bind(track: Track?, position: Int) = with(binding) {
            txtTrackNumber.text = position.toString()
            txtTrackName.text = track?.name
        }
    }
}

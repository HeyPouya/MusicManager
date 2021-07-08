package ir.heydarii.musicmanager.features.albumdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.pojos.TrackEntity
import kotlinx.android.synthetic.main.tracks_layout_item.view.*

/**
 * Adapter to show tracks of songs
 */
class TracksAdapter(val list: List<TrackEntity>) :
    RecyclerView.Adapter<TracksAdapter.TracksViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {

        /**
         * inflates views for recycler view
         */
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.tracks_layout_item, parent, false)
        return TracksViewHolder(view)
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
    class TracksViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        /**
         * Sets texts in views
         */
        fun bind(track: TrackEntity, position: Int) {
            view.txtTrackNumber.text = position.toString()
            view.txtTrackName.text = track.name
        }
    }
}

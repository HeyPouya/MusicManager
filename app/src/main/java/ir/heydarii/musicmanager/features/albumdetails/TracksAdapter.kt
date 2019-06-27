package ir.heydarii.musicmanager.features.albumdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.heydarii.musicmanager.R
import kotlinx.android.synthetic.main.tracks_layout_item.view.*

class TracksAdapter(val list: List<String>) : RecyclerView.Adapter<TracksAdapter.TracksViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tracks_layout_item, parent, false)
        return TracksViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        holder.bind(list[position], position)
    }


    class TracksViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(track: String, position: Int) {

            view.txtTrackNumber.text = position.toString()
            view.txtTrackName.text = track
        }

    }
}
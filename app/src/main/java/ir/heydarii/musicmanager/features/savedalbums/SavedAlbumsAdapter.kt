package ir.heydarii.musicmanager.features.savedalbums

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import com.squareup.picasso.Picasso
import ir.heydarii.musicmanager.R
import ir.heydarii.musicmanager.pojos.AlbumDatabaseEntity
import kotlinx.android.synthetic.main.saved_layout_item.view.*
import java.io.File

class SavedAlbumsAdapter(var list: List<AlbumDatabaseEntity>, private var clickListener: (String, String) -> Unit) :
    RecyclerView.Adapter<SavedAlbumsAdapter.SearchArtistViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchArtistViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.saved_layout_item, parent, false)
        return SearchArtistViewHolder(view, clickListener)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: SearchArtistViewHolder, position: Int) {
        holder.bind(list[position])
    }


    class SearchArtistViewHolder(private val view: View, var clickListener: (String, String) -> Unit) :
        RecyclerView.ViewHolder(view) {
        fun bind(album: AlbumDatabaseEntity) {
            view.txtName.text = album.albumName

            Logger.d(album.albumName)
            // Last image has always the best quality
            if (!album.image.isNullOrEmpty()) {
                val file = File(album.image)
                if (file.exists())
                    Picasso.get().load(file).placeholder(R.drawable.ic_album_placeholder).into(view.imgArtist)
            }

            view.setOnClickListener {
                clickListener(album.artistName, album.albumName)
            }
        }


    }
}
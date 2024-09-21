package uz.futuresoft.searchfind.ui.screens.main.add.adapters.picture.viewHolder

import android.net.Uri
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.futuresoft.searchfind.databinding.RecyclerViewItemPictureBinding
import uz.futuresoft.searchfind.ui.screens.main.add.adapters.picture.PictureAdapterListener

class PictureAdapterViewHolder(
    private val binding: RecyclerViewItemPictureBinding,
    private val listener: PictureAdapterListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Uri, position: Int) {
        binding.image.loadImage(uri = item)

        binding.remove.setOnClickListener {
            listener.onRemovePictureClicked(item = item, position = position)
        }
    }

    private fun ImageView.loadImage(uri: Uri) {
        Glide.with(binding.root.context).load(uri).into(this)
    }
}
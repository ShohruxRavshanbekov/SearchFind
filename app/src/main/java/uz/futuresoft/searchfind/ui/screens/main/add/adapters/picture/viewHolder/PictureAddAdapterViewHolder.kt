package uz.futuresoft.searchfind.ui.screens.main.add.adapters.picture.viewHolder

import androidx.recyclerview.widget.RecyclerView
import uz.futuresoft.searchfind.databinding.RecyclerViewItemPictureAddBinding
import uz.futuresoft.searchfind.ui.screens.main.add.adapters.picture.PictureAdapterListener

class PictureAddAdapterViewHolder(
    private val binding: RecyclerViewItemPictureAddBinding,
    private val listener: PictureAdapterListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        binding.addPicture.setOnClickListener {
            listener.onAddPictureClicked()
        }
    }
}
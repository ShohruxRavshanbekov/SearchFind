package uz.futuresoft.searchfind.ui.screens.main.add.adapters.picture

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.futuresoft.searchfind.databinding.RecyclerViewItemPictureAddBinding
import uz.futuresoft.searchfind.databinding.RecyclerViewItemPictureBinding
import uz.futuresoft.searchfind.ui.screens.main.add.adapters.picture.viewHolder.PictureAdapterViewHolder
import uz.futuresoft.searchfind.ui.screens.main.add.adapters.picture.viewHolder.PictureAddAdapterViewHolder

class PictureAdapter(
    private val listener: PictureAdapterListener,
) : ListAdapter<Uri, RecyclerView.ViewHolder>(PictureAdapterDiffUtilCallBack) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            Uri.EMPTY -> 0
            else -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val item = RecyclerViewItemPictureAddBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                PictureAddAdapterViewHolder(binding = item, listener = listener)
            }

            1 -> {
                val item = RecyclerViewItemPictureBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                PictureAdapterViewHolder(binding = item, listener = listener)
            }

            else -> {
                val item = RecyclerViewItemPictureBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                PictureAdapterViewHolder(binding = item, listener = listener)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> {
                val pictureAddAdapterViewHolder: PictureAddAdapterViewHolder =
                    holder as PictureAddAdapterViewHolder
                pictureAddAdapterViewHolder.bind()
            }

            1 -> {
                val pictureAdapterViewHolder: PictureAdapterViewHolder =
                    holder as PictureAdapterViewHolder
                pictureAdapterViewHolder.bind(item = getItem(position), position = position)

            }
        }
    }
}
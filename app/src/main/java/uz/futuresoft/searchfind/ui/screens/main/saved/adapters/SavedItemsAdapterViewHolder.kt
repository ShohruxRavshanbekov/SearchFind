package uz.futuresoft.searchfind.ui.screens.main.saved.adapters

import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.futuresoft.searchfind.R
import uz.futuresoft.searchfind.databinding.RecyclerViewItemItemBinding
import uz.futuresoft.searchfind.models.ItemModel
import uz.futuresoft.searchfind.utils.Constants

class SavedItemsAdapterViewHolder(
    private val binding: RecyclerViewItemItemBinding,
    private val listener: SavedItemsAdapterListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ItemModel) {
        binding.picture.loadImage(url = item.picture)
        binding.title.text = item.title
        binding.description.text = item.description
        binding.statusText.text = item.status
        binding.date.text = item.date
        binding.status.setBackground(status = item.status)
        binding.saved.setSavedState(state = item.saved)

        binding.item.setOnClickListener {
            listener.onItemClicked(item = item)
        }

        binding.btnSaved.setOnClickListener {
            var isSaved = item.saved
            isSaved = !isSaved
            binding.saved.setSavedState(state = isSaved)
            listener.onSavedClicked(item = item, state = isSaved)
        }
    }

    private fun ImageView.loadImage(url: String) {
        Glide.with(binding.root.context)
            .load(url)
            .placeholder(R.drawable.app_logo_for_splash)
            .into(this)
    }

    private fun ImageView.setSavedState(state: Boolean) {
        if (state)
            this.setImageResource(R.drawable.ic_bookmark_filled)
        else
            this.setImageResource(R.drawable.ic_bookmark)
    }

    private fun CardView.setBackground(status: String) {
        when (status) {
            Constants.LOST -> {
                this.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.error
                    )
                )
            }

            Constants.FOUND -> {
                this.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.success
                    )
                )
            }
        }
    }
}
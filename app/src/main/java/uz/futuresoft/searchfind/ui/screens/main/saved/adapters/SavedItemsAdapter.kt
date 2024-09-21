package uz.futuresoft.searchfind.ui.screens.main.saved.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import uz.futuresoft.searchfind.databinding.RecyclerViewItemItemBinding
import uz.futuresoft.searchfind.models.ItemModel

class SavedItemsAdapter(
    private val listener: SavedItemsAdapterListener,
) : ListAdapter<ItemModel, SavedItemsAdapterViewHolder>(SavedItemsAdapterDiffUtilCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedItemsAdapterViewHolder {
        val item = RecyclerViewItemItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SavedItemsAdapterViewHolder(binding = item, listener = listener)
    }

    override fun onBindViewHolder(holder: SavedItemsAdapterViewHolder, position: Int) {
        holder.bind(item = getItem(position))
    }
}
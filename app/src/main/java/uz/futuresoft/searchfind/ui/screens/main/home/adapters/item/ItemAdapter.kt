package uz.futuresoft.searchfind.ui.screens.main.home.adapters.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import uz.futuresoft.searchfind.databinding.RecyclerViewItemItemBinding
import uz.futuresoft.searchfind.models.ItemModel

class ItemAdapter(
    private val listener: ItemAdapterListener,
) : ListAdapter<ItemModel, ItemAdapterViewHolder>(ItemAdapterDiffUtilCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapterViewHolder {
        val item = RecyclerViewItemItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemAdapterViewHolder(binding = item, listener = listener)
    }

    override fun onBindViewHolder(holder: ItemAdapterViewHolder, position: Int) {
        holder.bind(item = getItem(position))
    }
}
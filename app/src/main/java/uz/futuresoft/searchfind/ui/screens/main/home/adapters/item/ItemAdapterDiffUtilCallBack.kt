package uz.futuresoft.searchfind.ui.screens.main.home.adapters.item

import androidx.recyclerview.widget.DiffUtil
import uz.futuresoft.searchfind.models.CategoryModel
import uz.futuresoft.searchfind.models.ItemModel

object ItemAdapterDiffUtilCallBack : DiffUtil.ItemCallback<ItemModel>() {
    override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
        return oldItem == newItem
    }
}
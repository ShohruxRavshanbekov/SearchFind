package uz.futuresoft.searchfind.ui.screens.main.home.adapters.category

import androidx.recyclerview.widget.DiffUtil
import uz.futuresoft.searchfind.models.CategoryModel

object CategoryAdapterDiffUtilCallBack : DiffUtil.ItemCallback<CategoryModel>() {
    override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
        return oldItem == newItem
    }
}
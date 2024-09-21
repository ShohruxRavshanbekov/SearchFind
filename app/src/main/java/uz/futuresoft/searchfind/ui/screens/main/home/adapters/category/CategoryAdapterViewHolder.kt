package uz.futuresoft.searchfind.ui.screens.main.home.adapters.category

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.futuresoft.searchfind.R
import uz.futuresoft.searchfind.databinding.RecyclerViewItemCategoryBinding
import uz.futuresoft.searchfind.models.CategoryModel

class CategoryAdapterViewHolder(
    private val binding: RecyclerViewItemCategoryBinding,
    private val adapter: ListAdapter<CategoryModel, CategoryAdapterViewHolder>,
    private val listener: CategoryAdapterListener,
) : RecyclerView.ViewHolder(binding.root) {

    private var rowIndex = 0

    fun bind(category: CategoryModel, position: Int) {
        binding.itemText.text = category.name
        if (rowIndex == position) selectItem() else unselectItem()

        binding.item.setOnClickListener {
            rowIndex = position
            adapter.notifyDataSetChanged()
            listener.onCategoryClicked(category = category)
        }
    }

    private fun selectItem() {
        binding.item.setCardBackgroundColor(
            ContextCompat.getColor(binding.root.context, R.color.primary)
        )
        binding.itemText.setTextColor(
            ContextCompat.getColor(binding.root.context, R.color.white)
        )
        rowIndex = -1
    }

    private fun unselectItem() {
        binding.item.setCardBackgroundColor(
            ContextCompat.getColor(binding.root.context, R.color.white)
        )
        binding.itemText.setTextColor(
            ContextCompat.getColor(binding.root.context, R.color.primary)
        )
    }
}
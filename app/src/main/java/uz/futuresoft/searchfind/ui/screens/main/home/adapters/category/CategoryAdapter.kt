package uz.futuresoft.searchfind.ui.screens.main.home.adapters.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import uz.futuresoft.searchfind.databinding.RecyclerViewItemCategoryBinding
import uz.futuresoft.searchfind.models.CategoryModel

class CategoryAdapter(
    private val listener: CategoryAdapterListener,
) : ListAdapter<CategoryModel, CategoryAdapterViewHolder>(CategoryAdapterDiffUtilCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapterViewHolder {
        val categoryItem = RecyclerViewItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryAdapterViewHolder(
            binding = categoryItem,
            adapter = this,
            listener = listener
        )
    }

    override fun onBindViewHolder(holder: CategoryAdapterViewHolder, position: Int) {
        holder.bind(category = getItem(position), position = position)
    }
}
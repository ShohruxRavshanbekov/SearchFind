package uz.futuresoft.searchfind.ui.screens.main.home.adapters.category

import uz.futuresoft.searchfind.models.CategoryModel

interface CategoryAdapterListener {
    fun onCategoryClicked(category: CategoryModel)
}
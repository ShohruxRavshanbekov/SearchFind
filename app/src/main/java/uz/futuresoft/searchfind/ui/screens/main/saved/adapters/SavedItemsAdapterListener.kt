package uz.futuresoft.searchfind.ui.screens.main.saved.adapters

import uz.futuresoft.searchfind.models.ItemModel

interface SavedItemsAdapterListener {
    fun onItemClicked(item: ItemModel)
    fun onSavedClicked(item: ItemModel, state: Boolean)
}
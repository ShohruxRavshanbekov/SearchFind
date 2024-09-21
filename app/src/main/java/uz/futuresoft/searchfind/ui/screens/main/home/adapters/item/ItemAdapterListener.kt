package uz.futuresoft.searchfind.ui.screens.main.home.adapters.item

import uz.futuresoft.searchfind.models.ItemModel

interface ItemAdapterListener {
    fun onItemClicked(item: ItemModel)
    fun onSavedClicked(item: ItemModel, state: Boolean)
}
package uz.futuresoft.searchfind.ui.screens.main.add.adapters.picture

import android.net.Uri

interface PictureAdapterListener {
    fun onAddPictureClicked()
    fun onRemovePictureClicked(item: Uri, position: Int)
}
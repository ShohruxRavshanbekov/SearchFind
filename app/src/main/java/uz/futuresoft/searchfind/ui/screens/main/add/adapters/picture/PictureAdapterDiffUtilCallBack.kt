package uz.futuresoft.searchfind.ui.screens.main.add.adapters.picture

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil
import uz.futuresoft.searchfind.models.ItemModel

object PictureAdapterDiffUtilCallBack : DiffUtil.ItemCallback<Uri>() {
    override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
        return oldItem == newItem
    }
}
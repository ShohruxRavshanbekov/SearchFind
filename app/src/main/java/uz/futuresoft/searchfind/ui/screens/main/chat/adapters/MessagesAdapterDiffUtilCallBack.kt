package uz.futuresoft.searchfind.ui.screens.main.chat.adapters

import androidx.recyclerview.widget.DiffUtil
import uz.futuresoft.searchfind.models.MessageModel

object MessagesAdapterDiffUtilCallBack : DiffUtil.ItemCallback<MessageModel>() {
    override fun areItemsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
        return oldItem == newItem
    }
}
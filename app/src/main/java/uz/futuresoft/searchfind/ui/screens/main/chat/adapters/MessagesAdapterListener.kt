package uz.futuresoft.searchfind.ui.screens.main.chat.adapters

import android.view.View
import uz.futuresoft.searchfind.models.MessageModel

interface MessagesAdapterListener {
    fun onItemLongClicked(view: View, message: MessageModel)
    fun onCallClicked(message: MessageModel)
}
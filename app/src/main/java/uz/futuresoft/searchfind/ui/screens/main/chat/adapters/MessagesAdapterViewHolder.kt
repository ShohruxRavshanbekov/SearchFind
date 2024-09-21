package uz.futuresoft.searchfind.ui.screens.main.chat.adapters

import androidx.recyclerview.widget.RecyclerView
import uz.futuresoft.searchfind.databinding.RecyclerViewItemMessageBinding
import uz.futuresoft.searchfind.models.MessageModel

class MessagesAdapterViewHolder(
    private val binding: RecyclerViewItemMessageBinding,
    private val listener: MessagesAdapterListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(message: MessageModel) {
        binding.itemTitle.text = message.item
        binding.founder.text = message.founder
        binding.phoneNumber.text = message.contactNumber

        binding.item.setOnLongClickListener {
            listener.onItemLongClicked(view = it, message = message)
            true
        }

        binding.call.setOnClickListener {
            listener.onCallClicked(message = message)
        }
    }
}
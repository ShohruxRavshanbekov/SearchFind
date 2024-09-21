package uz.futuresoft.searchfind.ui.screens.main.chat.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import uz.futuresoft.searchfind.databinding.RecyclerViewItemMessageBinding
import uz.futuresoft.searchfind.models.MessageModel

class MessagesAdapter(
    private val listener: MessagesAdapterListener,
) : ListAdapter<MessageModel, MessagesAdapterViewHolder>(MessagesAdapterDiffUtilCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesAdapterViewHolder {
        val item = RecyclerViewItemMessageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MessagesAdapterViewHolder(binding = item, listener = listener)
    }

    override fun onBindViewHolder(holder: MessagesAdapterViewHolder, position: Int) {
        holder.bind(message = getItem(position))
    }
}
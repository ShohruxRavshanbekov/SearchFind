package uz.futuresoft.searchfind.ui.screens.main.chat

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.futuresoft.searchfind.R
import uz.futuresoft.searchfind.common.SharedViewModel
import uz.futuresoft.searchfind.common.dialogs.LoadingDialog
import uz.futuresoft.searchfind.databinding.ScreenChatBinding
import uz.futuresoft.searchfind.models.MessageModel
import uz.futuresoft.searchfind.ui.screens.main.chat.adapters.MessagesAdapter
import uz.futuresoft.searchfind.ui.screens.main.chat.adapters.MessagesAdapterListener
import uz.futuresoft.searchfind.utils.showToast

@AndroidEntryPoint
class ChatScreen : Fragment(), MessagesAdapterListener {

    private val binding by lazy { ScreenChatBinding.inflate(layoutInflater) }

    private val viewModel: ChatScreenViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val loadingDialog by lazy { LoadingDialog(context = requireContext()) }

    private val messagesAdapter by lazy { MessagesAdapter(listener = this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMessages(userId = viewModel.getUserId())
        checkUser()
        updateUi()
        binding.messages.adapter = messagesAdapter

        binding.logIn.setOnClickListener {
            sharedViewModel.navigate(id = R.id.action_mainScreen_to_authScreen)
        }
    }

    override fun onItemLongClicked(view: View, message: MessageModel) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.message_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete_message -> viewModel.deleteMessage(id = message.id)
            }
            true
        }

        popupMenu.show()
    }

    override fun onCallClicked(message: MessageModel) {
        call(number = message.contactNumber)
    }

    private fun checkUser() {
        if (viewModel.getUserId().isNotEmpty()) {
            binding.messages.isVisible = true
            binding.emptyProfile.isVisible = false
//            binding.search.isVisible = true
        } else {
            binding.messages.isVisible = false
            binding.emptyProfile.isVisible = true
//            binding.search.isVisible = false
        }
    }

    private fun updateUi() {
        sharedViewModel.logOut.observe(viewLifecycleOwner) {
            checkUser()
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) loadingDialog.show() else loadingDialog.dismiss()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showToast(message = getString(R.string.something_went_wrong))
        }

        viewModel.messages.observe(viewLifecycleOwner) {
            messagesAdapter.submitList(it)
        }
    }

    private fun call(number: String) {
        val uri = Uri.parse("tel: ${Uri.encode(number)}")
        val intent = Intent(Intent.ACTION_DIAL, uri)
        startActivity(intent)
    }
}
package uz.futuresoft.searchfind.ui.screens.itemDetail

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import uz.futuresoft.searchfind.R
import uz.futuresoft.searchfind.common.dialogs.LoadingDialog
import uz.futuresoft.searchfind.databinding.ScreenItemDetailBinding
import uz.futuresoft.searchfind.models.ItemModel
import uz.futuresoft.searchfind.models.MessageModel
import uz.futuresoft.searchfind.models.UserModel
import uz.futuresoft.searchfind.utils.Constants
import uz.futuresoft.searchfind.utils.showToast

@AndroidEntryPoint
class ItemDetailScreen : Fragment() {

    private val binding by lazy { ScreenItemDetailBinding.inflate(layoutInflater) }

    private val viewModel: ItemDetailScreenViewModel by viewModels()

    private val loadingDialog by lazy { LoadingDialog(context = requireContext()) }

    private lateinit var itemId: String
    private lateinit var item: ItemModel
    private lateinit var user: UserModel

    private var itemState = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemId = arguments?.getString("itemId") ?: ""
        viewModel.getItem(id = itemId)
        updateUi()

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

//        binding.chat.setOnClickListener {
//            showToast(message = "Chat")
//        }

        binding.btnSaved.setOnClickListener {
            itemState = !itemState
            if (itemState) {
                viewModel.addToSaved(itemId = item.id)
            } else {
                viewModel.removeFromSaved(itemId = item.id)
            }
        }

        binding.phoneNumberContainer.setOnClickListener {
            call(number = binding.phoneNumber.text.toString())
        }

        binding.email.setOnClickListener {
            openEmail(email = binding.email.text.toString())
        }

        binding.telegram.setOnClickListener {
            openTelegram(account = binding.telegram.text.toString())
        }

        binding.instagram.setOnClickListener {
            openInstagram(account = binding.instagram.text.toString())
        }

        binding.notify.setOnClickListener {
            sendNotification()
        }
    }

    private fun sendNotification() {
        val message = MessageModel(
            itemOwnerId = item.ownerId,
            founder = "${user.name} ${user.surname}",
            item = item.title,
            contactNumber = user.phoneNumber
        )

        viewModel.sendMessage(message = message)
    }

    private fun updateUi() {
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) loadingDialog.show() else loadingDialog.dismiss()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showToast(message = getString(R.string.something_went_wrong))
        }

        viewModel.user.observe(viewLifecycleOwner) { user ->
            this.user = user
        }

        viewModel.item.observe(viewLifecycleOwner) { item ->
            this.item = item
            itemState = item.saved
            viewModel.getUser(id = viewModel.getUserId())

            binding.content.isVisible = true
            binding.notify.isVisible = item.ownerId != viewModel.getUserId()
            binding.picture.loadPicture(url = item.picture)
            binding.saved.setSavedState(state = item.saved)
            binding.status.setBackground(status = item.status)
            if (item.status == Constants.LOST)
                binding.placeOfLostOrFound.text = getString(R.string.place_of_lost)
            else
                binding.placeOfLostOrFound.text = getString(R.string.place_of_found)
            binding.title.text = item.title
            binding.statusText.text = item.status
            binding.description.text = item.description
            binding.place.text = item.place
            binding.phoneNumber.text = item.contactInfo.phoneNumber
            binding.email.text = item.contactInfo.email
            binding.telegram.text = item.contactInfo.telegram
            binding.instagram.text = item.contactInfo.instagram
            binding.contactInfoContainer.isVisible = item.contactInfo.phoneNumber.isNotEmpty() ||
                    item.contactInfo.email.isNotEmpty() ||
                    item.contactInfo.telegram.isNotEmpty() ||
                    item.contactInfo.instagram.isNotEmpty()
            binding.phoneNumberContainer.isVisible = item.contactInfo.phoneNumber.isNotEmpty()
            binding.emailContainer.isVisible = item.contactInfo.email.isNotEmpty()
            binding.telegramContainer.isVisible = item.contactInfo.telegram.isNotEmpty()
            binding.instagramContainer.isVisible = item.contactInfo.instagram.isNotEmpty()
        }

        viewModel.result.observe(viewLifecycleOwner) {
            viewModel.getItem(id = itemId)
        }

        viewModel.addMessageResult.observe(viewLifecycleOwner) {
            showToast(message = getString(R.string.notification_sended))
        }
    }

    private fun ImageView.loadPicture(url: String) {
        Glide.with(binding.root.context)
            .load(url)
            .placeholder(R.drawable.app_logo_for_splash)
            .into(this)
    }

    private fun ImageView.setSavedState(state: Boolean) {
        if (state)
            this.setImageResource(R.drawable.ic_bookmark_filled)
        else
            this.setImageResource(R.drawable.ic_bookmark)
    }

    private fun CardView.setBackground(status: String) {
        when (status) {
            Constants.LOST -> {
                this.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.error
                    )
                )
            }

            Constants.FOUND -> {
                this.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.success
                    )
                )
            }
        }
    }

    private fun call(number: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel: ${Uri.encode(number)}"))
        startActivity(intent)
    }

    private fun openEmail(email: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        intent.setType("message/rfc822")
        startActivity(Intent.createChooser(intent, "Choose an Email client :"))
    }

    private fun openTelegram(account: String) {
        val userAccount =
            if (account.first() == '@') account.substring(1, account.length) else account
        val uri = Uri.parse("https://t.me/$userAccount")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun openInstagram(account: String) {
        val uri = Uri.parse("https://www.instagram.com/$account")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}
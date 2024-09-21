package uz.futuresoft.searchfind.ui.screens.main.profile

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import uz.futuresoft.searchfind.R
import uz.futuresoft.searchfind.common.SharedViewModel
import uz.futuresoft.searchfind.common.dialogs.LoadingDialog
import uz.futuresoft.searchfind.common.dialogs.LogOutDialog
import uz.futuresoft.searchfind.databinding.ScreenProfileBinding
import uz.futuresoft.searchfind.ui.screens.main.profile.dialogs.appLanguage.AppLanguageBottomSheetDialog
import uz.futuresoft.searchfind.ui.screens.main.profile.dialogs.notifiaction.NotificationBottomSheetDialog
import uz.futuresoft.searchfind.utils.showToast

@AndroidEntryPoint
class ProfileScreen : Fragment(), LogOutDialog.Listener {

    private val binding by lazy { ScreenProfileBinding.inflate(layoutInflater) }

    private val viewModel: ProfileScreenViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val loadingDialog by lazy { LoadingDialog(context = requireContext()) }
    private val logOutDialog by lazy { LogOutDialog(context = requireContext(), listener = this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.getLanguage() == "") viewModel.setLanguage(language = "us")
        checkUser()
        updateUi()

        binding.logOut.setOnClickListener {
            logOutDialog.show()
        }

        binding.logIn.setOnClickListener {
            sharedViewModel.navigate(id = R.id.action_mainScreen_to_authScreen)
        }

        binding.edit.setOnClickListener {
            sharedViewModel.navigate(id = R.id.action_mainScreen_to_editUserInfoScreen)
        }

        binding.notifications.setOnClickListener {
            sharedViewModel.navigate(id = R.id.action_mainScreen_to_notificationBottomSheetDialog)
        }

        binding.language.setOnClickListener {
            sharedViewModel.navigate(id = R.id.action_mainScreen_to_appLanguageBottomSheetDialog)
        }
    }

    override fun onYesClicked() {
        viewModel.logOut()
        sharedViewModel.logOut()
        checkUser()
        logOutDialog.dismiss()
    }

    private fun checkUser() {
        if (viewModel.getUserId().isNotEmpty()) {
            binding.content.isVisible = true
            binding.logOut.isVisible = true
            binding.emptyProfile.isVisible = false
            viewModel.getUser(id = viewModel.getUserId())
        } else {
            binding.content.isVisible = false
            binding.logOut.isVisible = false
            binding.emptyProfile.isVisible = true
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUi() {
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) loadingDialog.show() else loadingDialog.dismiss()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showToast(message = getString(R.string.something_went_wrong))
        }

        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.picture.loadImage(picture = user.picture)
            binding.fullName.text = "${user.name} ${user.surname}"
            binding.phoneNumber.text = formatPhoneNumber(phoneNumber = user.phoneNumber)
            binding.currentLanguage.text = when (viewModel.getLanguage()) {
                "uz" -> getString(R.string.uzbek)
                "us" -> getString(R.string.english)
                "ru" -> getString(R.string.russian)
                else -> ""
            }
        }
    }

    private fun ImageView.loadImage(picture: String) {
        Glide.with(requireContext())
            .load(picture)
            .placeholder(R.drawable.default_user_picture)
            .into(this)
    }

    private fun formatPhoneNumber(phoneNumber: String) =
        PhoneNumberUtils.formatNumber(phoneNumber, "UZ")
}
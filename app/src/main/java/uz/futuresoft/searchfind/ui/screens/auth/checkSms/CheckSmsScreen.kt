package uz.futuresoft.searchfind.ui.screens.auth.checkSms

import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fraggjkee.smsconfirmationview.SmsConfirmationView
import dagger.hilt.android.AndroidEntryPoint
import uz.futuresoft.searchfind.R
import uz.futuresoft.searchfind.common.SharedViewModel
import uz.futuresoft.searchfind.common.dialogs.LoadingDialog
import uz.futuresoft.searchfind.databinding.ScreenCheckSmsBinding
import uz.futuresoft.searchfind.utils.showToast

@AndroidEntryPoint
class CheckSmsScreen : Fragment() {

    private val binding by lazy { ScreenCheckSmsBinding.inflate(layoutInflater) }

    private val viewModel: CheckSmsScreenViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val loadingDialog by lazy { LoadingDialog(context = requireContext()) }

    private var verificationId: String = ""
    private var phoneNumber: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        verificationId = arguments?.getString("verificationId")!!
        phoneNumber = arguments?.getString("phoneNumber")!!
        binding.phoneNumber.text = formatPhoneNumber(phoneNumber = phoneNumber)
        updateUi()

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.smsCodeView.onChangeListener =
            SmsConfirmationView.OnChangeListener { code, isComplete ->
                if (isComplete)
                    viewModel.getUserId(verificationId = verificationId, code = code)
            }
    }

    private fun updateUi() {
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) loadingDialog.show() else loadingDialog.dismiss()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            if (it == "invalid credential")
                showToast(message = getString(R.string.the_code_did_not_match))
            else
                showToast(message = getString(R.string.something_went_wrong))
        }

        viewModel.userId.observe(viewLifecycleOwner) { userId ->
            viewModel.getUser(id = userId)
        }

        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user.id.isNotEmpty())
                sharedViewModel.popBackStack()
            else
                findNavController().navigate(
                    R.id.action_checkSmsScreen_to_userDataScreen,
                    bundleOf("phoneNumber" to phoneNumber)
                )
        }
    }

    private fun formatPhoneNumber(phoneNumber: String) =
        PhoneNumberUtils.formatNumber(phoneNumber, "UZ")
}
package uz.futuresoft.searchfind.ui.screens.auth.logIn

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.futuresoft.searchfind.R
import uz.futuresoft.searchfind.common.dialogs.LoadingDialog
import uz.futuresoft.searchfind.databinding.ScreenLogInBinding
import uz.futuresoft.searchfind.utils.showToast

@AndroidEntryPoint
class LogInScreen : Fragment() {

    private val binding by lazy { ScreenLogInBinding.inflate(layoutInflater) }

    private val viewModel: LogInScreenViewModel by viewModels()

    private val loadingDialog by lazy { LoadingDialog(context = requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUi()

        binding.phoneNumber.addTextChangedListener(onTextChanged = { text, _, _, _ ->
            binding.next.isEnabled = text.toString().length == 17
        })

        binding.next.setOnClickListener {
            val phoneNumber = binding.phoneNumber.unMaskedText.toString().trim()
            viewModel.authenticate(activity = requireActivity(), phoneNumber = phoneNumber)
        }
    }

    private fun updateUi() {
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) loadingDialog.show() else loadingDialog.dismiss()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showToast(message = getString(R.string.something_went_wrong))
        }

        viewModel.verificationId.observe(viewLifecycleOwner) { verificationId ->
            findNavController().navigate(
                R.id.action_logInScreen_to_checkSmsScreen,
                bundleOf(
                    "verificationId" to verificationId,
                    "phoneNumber" to binding.phoneNumber.unMaskedText.toString().trim()
                )
            )
        }
    }
}
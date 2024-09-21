package uz.futuresoft.searchfind.ui.screens.auth.userData

import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import uz.futuresoft.searchfind.R
import uz.futuresoft.searchfind.common.SharedViewModel
import uz.futuresoft.searchfind.common.dialogs.LoadingDialog
import uz.futuresoft.searchfind.databinding.ScreenUserDataBinding
import uz.futuresoft.searchfind.models.UserModel
import uz.futuresoft.searchfind.utils.showToast

@AndroidEntryPoint
class UserDataScreen : Fragment() {

    private val binding by lazy { ScreenUserDataBinding.inflate(layoutInflater) }

    private val viewModel: UserDataScreenViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val loadingDialog by lazy { LoadingDialog(context = requireContext()) }

    private var userPicture: Uri? = null
    private var phoneNumber: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        phoneNumber = arguments?.getString("phoneNumber")!!
        updateUi()

        binding.addPicture.setOnClickListener {
            photoTakerFromGallery.launch("image/*")
        }

        binding.name.addTextChangedListener(onTextChanged = { text, _, _, _ ->
            binding.createAccount.isEnabled = text.toString().isNotEmpty()
        })

        binding.createAccount.setOnClickListener {
            createAccount()
        }
    }

    private fun updateUi() {
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) loadingDialog.show() else loadingDialog.dismiss()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showToast(message = getString(R.string.something_went_wrong))
        }

        viewModel.result.observe(viewLifecycleOwner) {
            showToast(message = getString(R.string.data_saved_successfully))
            sharedViewModel.popBackStack()
        }
    }

    private fun createAccount() {
        val name = binding.name.text.toString().trim()
        val surname = binding.surname.text.toString().trim()
        val user = UserModel(name = name, surname = surname, phoneNumber = phoneNumber)

        viewModel.addUser(picture = userPicture, user = user)
    }

    private val photoTakerFromGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                userPicture = uri
                binding.addPicture.isVisible = false
                binding.picture.isVisible = true
                binding.picture.loadImage(uri = userPicture!!)
            }
        }

    private fun ImageView.loadImage(uri: Uri) {
        Glide.with(requireContext()).load(uri).into(this)
    }
}
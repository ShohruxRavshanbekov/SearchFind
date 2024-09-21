package uz.futuresoft.searchfind.ui.screens.editUserInfo

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
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import uz.futuresoft.searchfind.R
import uz.futuresoft.searchfind.common.dialogs.LoadingDialog
import uz.futuresoft.searchfind.databinding.ScreenEditUserInfoBinding
import uz.futuresoft.searchfind.models.ItemModel
import uz.futuresoft.searchfind.models.SocialMediaModel
import uz.futuresoft.searchfind.models.UserModel
import uz.futuresoft.searchfind.utils.showToast

@AndroidEntryPoint
class EditUserInfoScreen : Fragment() {

    private val binding by lazy { ScreenEditUserInfoBinding.inflate(layoutInflater) }

    private val viewModel: EditUserInfoScreenViewModel by viewModels()

    private val loadingDialog by lazy { LoadingDialog(context = requireContext()) }

    private var picture: Uri? = null
    private var user: UserModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUser(id = viewModel.getUserId())
        updateUi()

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.name.addTextChangedListener(onTextChanged = { text, _, _, _ ->
            binding.save.isEnabled = text.toString().isNotEmpty()
        })

        binding.remove.setOnClickListener {
            viewModel.removePicture(
                picture = null,
                user = UserModel(
                    id = user!!.id,
                    name = user!!.name,
                    surname = user!!.surname,
                    picture = "",
                    phoneNumber = user!!.phoneNumber
                )
            )
        }

        binding.update.setOnClickListener {
            photoTakerFromGallery.launch("image/*")
        }

        binding.save.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        val name = binding.name.text.toString().trim()
        val surname = binding.surname.text.toString().trim()

        val user = UserModel(
            id = user!!.id,
            name = name,
            surname = surname,
            phoneNumber = user!!.phoneNumber
        )
        viewModel.editUser(picture = picture, user = user)
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
            binding.picture.loadImage(picture = user.picture)
            binding.name.setText(user.name)
            binding.surname.setText(user.surname)
        }

        viewModel.editUserDataResult.observe(viewLifecycleOwner) {
            showToast(message = getString(R.string.data_saved_successfully))
            findNavController().popBackStack()
        }

        viewModel.removePictureResult.observe(viewLifecycleOwner) {
            viewModel.getUser(id = viewModel.getUserId())
        }
    }

    private val photoTakerFromGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                picture = uri
                binding.picture.loadImage(uri = picture!!)
            }
        }

    private fun ImageView.loadImage(uri: Uri) {
        Glide.with(requireContext())
            .load(uri)
            .placeholder(R.drawable.default_user_picture)
            .into(this)
    }

    private fun ImageView.loadImage(picture: String) {
        Glide.with(requireContext())
            .load(picture)
            .placeholder(R.drawable.default_user_picture)
            .into(this)
    }
}
package uz.futuresoft.searchfind.ui.screens.main.add

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import uz.futuresoft.searchfind.R
import uz.futuresoft.searchfind.common.SharedViewModel
import uz.futuresoft.searchfind.common.dialogs.LoadingDialog
import uz.futuresoft.searchfind.databinding.ScreenAddBinding
import uz.futuresoft.searchfind.models.CategoryModel
import uz.futuresoft.searchfind.models.ItemModel
import uz.futuresoft.searchfind.models.SocialMediaModel
import uz.futuresoft.searchfind.utils.Constants.FOUND
import uz.futuresoft.searchfind.utils.Constants.LOST
import uz.futuresoft.searchfind.utils.showToast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AddScreen : Fragment() {

    private val binding by lazy { ScreenAddBinding.inflate(layoutInflater) }

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel: AddScreenViewModel by viewModels()

    private val loadingDialog by lazy { LoadingDialog(context = requireContext()) }

    private val categories = ArrayList<CategoryModel>()
    private val statuses = listOf(LOST, FOUND)
    private val statusAdapter by lazy {
        ArrayAdapter(requireContext(), R.layout.drop_down_menu_item, statuses)
    }

    private var picture: Uri? = null
    private var title = ""
    private var place = ""
    private var category = ""
    private var status = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkUser()
        updateUi()
        binding.status.setAdapter(statusAdapter)

        binding.title.addTextChangedListener(onTextChanged = { text, _, _, _ ->
            title = text.toString().trim()
            checkFields()
        })

        binding.place.addTextChangedListener(onTextChanged = { text, _, _, _ ->
            place = text.toString().trim()
            checkFields()
        })

        binding.addPicture.setOnClickListener {
            photoTakerFromGallery.launch("image/*")
        }

        binding.category.setOnItemClickListener { _, _, position, _ ->
            category = categories[position].name
            checkFields()
        }

        binding.status.setOnItemClickListener { _, _, position, _ ->
            status = statuses[position]
            checkFields()
        }

        binding.btnPhone.setOnClickListener {
            showHideViews(indicator = binding.selectedIconPhone, container = binding.TILPhone)
        }

        binding.btnEmail.setOnClickListener {
            showHideViews(indicator = binding.selectedIconEmail, container = binding.TILEmail)
        }

        binding.btnTelegram.setOnClickListener {
            showHideViews(indicator = binding.selectedIconTelegram, container = binding.TILTelegram)
        }

        binding.btnInstagram.setOnClickListener {
            showHideViews(
                indicator = binding.selectedIconInstagram,
                container = binding.TILInstagram
            )
        }

        binding.btnSave.setOnClickListener {
            saveData()
        }

        binding.logIn.setOnClickListener {
            sharedViewModel.navigate(id = R.id.action_mainScreen_to_authScreen)
        }
    }

    private fun checkFields() {
        binding.btnSave.isEnabled =
            title.isNotEmpty() && place.isNotEmpty() && category.isNotEmpty() && status.isNotEmpty()
    }

    private fun saveData() {
        val title = binding.title.text.toString().trim()
        val description = binding.description.text.toString().trim()
        val place = binding.place.text.toString().trim()
        val category = categories.find { it.name == category }?.id ?: ""
        val date = getCurrentDate()
        val phone = binding.phone.text.toString().trim()
        val email = binding.email.text.toString().trim()
        val telegram = binding.telegram.text.toString().trim()
        val instagram = binding.instagram.text.toString().trim()

        val item = ItemModel(
            ownerId = viewModel.getUserId(),
            categoryId = category,
            title = title,
            description = description,
            place = place,
            date = date,
            status = status,
            contactInfo = SocialMediaModel(
                phoneNumber = phone,
                email = email,
                telegram = telegram,
                instagram = instagram
            )
        )
        viewModel.addItem(picture = picture, item = item)
    }

    private fun checkUser() {
        if (viewModel.getUserId().isNotEmpty()) {
            viewModel.getCategories()
            binding.nestedScrollView.isVisible = true
            binding.emptyProfile.isVisible = false
        } else {
            binding.nestedScrollView.isVisible = false
            binding.emptyProfile.isVisible = true
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

        viewModel.categories.observe(viewLifecycleOwner) {
            categories.clear()
            it.forEach { category ->
                if (category.id != "all") categories.add(category)
            }
            val categoryAdapter = ArrayAdapter(
                requireContext(),
                R.layout.drop_down_menu_item,
                categories.map { category -> category.name }
            )
            binding.category.setAdapter(categoryAdapter)
        }

        viewModel.addItemResult.observe(viewLifecycleOwner) {
            showToast(message = getString(R.string.successfully_added))
            sharedViewModel.setScreen(screenId = R.id.home)
            closeWindow()
        }
    }

    private fun showHideViews(indicator: ImageView, container: TextInputLayout) {
        indicator.isVisible = !indicator.isVisible
        container.isVisible = indicator.isVisible
    }

    private fun getCurrentDate(): String {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return formatter.format(time)
    }

    private val photoTakerFromGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                picture = uri
                binding.addPicture.isVisible = false
                binding.picture.isVisible = true
                binding.picture.loadImage(uri = picture!!)
            }
        }

    private fun ImageView.loadImage(uri: Uri) {
        Glide.with(requireContext())
            .load(uri)
            .placeholder(R.drawable.app_logo_for_splash)
            .into(this)
    }

    private fun closeWindow() {
        binding.title.text?.clear()
        binding.description.text?.clear()
        binding.place.text?.clear()
        binding.category.text?.clear()
        binding.status.text?.clear()
        binding.phone.text?.clear()
        binding.email.text?.clear()
        binding.telegram.text?.clear()
        binding.instagram.text?.clear()
        binding.btnSave.isEnabled = false
    }

    /*    private fun requestCameraPermission() {
            when (checkSelfPermission(requireContext(), CAMERA_PERMISSION)) {
                PackageManager.PERMISSION_GRANTED -> {
                    showToast(message = "permission granted")
                    photoTakerFromCamera.launch()
                }

                PackageManager.PERMISSION_DENIED -> {
                    showToast(message = "permission denied")
                    requestPermissionLauncher.launch(CAMERA_PERMISSION)
                }
            }
        }

        private fun requestPermissionLauncher() =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    showToast(message = "granted")
                } else {
                    showToast(message = "denied")
                }
            }

        private fun photoTakerFromCamera() =
                registerForActivityResult(ActivityResultContracts.TakePicture()) {

                }
    */
}
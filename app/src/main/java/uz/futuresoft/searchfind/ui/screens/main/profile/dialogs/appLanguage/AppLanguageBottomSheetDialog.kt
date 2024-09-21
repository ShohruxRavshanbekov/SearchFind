package uz.futuresoft.searchfind.ui.screens.main.profile.dialogs.appLanguage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import uz.futuresoft.searchfind.R
import uz.futuresoft.searchfind.databinding.BottomSheetDialogAppLanguageBinding

@AndroidEntryPoint
class AppLanguageBottomSheetDialog : BottomSheetDialogFragment() {

    private val binding by lazy { BottomSheetDialogAppLanguageBinding.inflate(layoutInflater) }

    private val viewModel: AppLanguageBottomSheetDialogViewModel by viewModels()

    override fun getTheme() = R.style.BottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheet = binding.bottomSheet
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        binding.tickUzb.isVisible = viewModel.getLanguage() == "uz"
        binding.tickUsa.isVisible = viewModel.getLanguage() == "us"
        binding.tickRus.isVisible = viewModel.getLanguage() == "ru"

        binding.close.setOnClickListener {
            dismiss()
        }

        binding.uzbek.setOnClickListener {
            viewModel.setLanguage(language = "uz")
            binding.tickUzb.isVisible = true
            binding.tickUsa.isVisible = false
            binding.tickRus.isVisible = false
            dismiss()
            requireActivity().recreate()
        }

        binding.english.setOnClickListener {
            viewModel.setLanguage(language = "us")
            binding.tickUzb.isVisible = false
            binding.tickUsa.isVisible = true
            binding.tickRus.isVisible = false
            dismiss()
            requireActivity().recreate()
        }

        binding.russian.setOnClickListener {
            viewModel.setLanguage(language = "ru")
            binding.tickUzb.isVisible = false
            binding.tickUsa.isVisible = false
            binding.tickRus.isVisible = true
            dismiss()
            requireActivity().recreate()
        }
    }
}
package uz.futuresoft.searchfind.ui.screens.main.profile.dialogs.notifiaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import uz.futuresoft.searchfind.R
import uz.futuresoft.searchfind.databinding.BottomSheetDialogNotificationBinding

@AndroidEntryPoint
class NotificationBottomSheetDialog : BottomSheetDialogFragment() {

    private val binding by lazy { BottomSheetDialogNotificationBinding.inflate(layoutInflater) }

//    private val viewModel: AppLanguageBottomSheetDialogViewModel by viewModels()

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

    }
}
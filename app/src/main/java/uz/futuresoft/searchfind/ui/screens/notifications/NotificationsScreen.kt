package uz.futuresoft.searchfind.ui.screens.notifications

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import uz.futuresoft.searchfind.R
import uz.futuresoft.searchfind.databinding.ScreenNotificationsBinding

@AndroidEntryPoint
class NotificationsScreen : Fragment() {

    private val binding by lazy { ScreenNotificationsBinding.inflate(layoutInflater) }

    private val viewModel: NotificationsScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
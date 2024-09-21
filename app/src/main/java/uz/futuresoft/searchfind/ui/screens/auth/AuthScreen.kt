package uz.futuresoft.searchfind.ui.screens.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.futuresoft.searchfind.common.SharedViewModel
import uz.futuresoft.searchfind.databinding.ScreenAuthBinding

@AndroidEntryPoint
class AuthScreen : Fragment() {

    private val binding by lazy { ScreenAuthBinding.inflate(layoutInflater) }

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUi()
    }

    private fun updateUi() {
        sharedViewModel.backStack.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }
}
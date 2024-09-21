package uz.futuresoft.searchfind.ui.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.futuresoft.searchfind.R
import uz.futuresoft.searchfind.common.SharedViewModel
import uz.futuresoft.searchfind.databinding.ScreenMainBinding

@AndroidEntryPoint
class MainScreen : Fragment() {

    private val binding by lazy { ScreenMainBinding.inflate(layoutInflater) }

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val navHostFragment by lazy {
        childFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
    }
    private val navController by lazy { navHostFragment.navController }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavigation.setupWithNavController(navController)
        observeChanges()
    }

    private fun observeChanges() {
        sharedViewModel.changeScreen.observe(viewLifecycleOwner) { screenId ->
            binding.bottomNavigation.selectedItemId = screenId
        }

        sharedViewModel.bottomNavigationState.observe(viewLifecycleOwner) { isVisible ->
            binding.bottomNavigation.isVisible = isVisible
        }

        sharedViewModel.navigate.observe(viewLifecycleOwner) { id ->
            findNavController().navigate(id)
        }

        sharedViewModel.navigateWithData.observe(viewLifecycleOwner) { navigationData ->
            findNavController().navigate(
                navigationData.id,
                bundleOf(navigationData.key to navigationData.data)
            )
        }
    }
}
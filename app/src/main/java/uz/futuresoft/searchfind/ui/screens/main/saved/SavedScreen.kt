package uz.futuresoft.searchfind.ui.screens.main.saved

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.futuresoft.searchfind.R
import uz.futuresoft.searchfind.common.SharedViewModel
import uz.futuresoft.searchfind.common.dialogs.LoadingDialog
import uz.futuresoft.searchfind.databinding.ScreenSavedBinding
import uz.futuresoft.searchfind.models.ItemModel
import uz.futuresoft.searchfind.ui.screens.main.home.adapters.item.ItemAdapter
import uz.futuresoft.searchfind.ui.screens.main.saved.adapters.SavedItemsAdapter
import uz.futuresoft.searchfind.ui.screens.main.saved.adapters.SavedItemsAdapterListener
import uz.futuresoft.searchfind.utils.NavigateWithData
import uz.futuresoft.searchfind.utils.showToast

@AndroidEntryPoint
class SavedScreen : Fragment(), SavedItemsAdapterListener {

    private val binding by lazy { ScreenSavedBinding.inflate(layoutInflater) }

    private val viewModel: SavedScreenViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val loadingDialog by lazy { LoadingDialog(context = requireContext()) }

    private val itemAdapter by lazy { SavedItemsAdapter(listener = this) }

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
        binding.savedItems.adapter = itemAdapter

        binding.logIn.setOnClickListener {
            sharedViewModel.navigate(id = R.id.action_mainScreen_to_authScreen)
        }
    }

    override fun onItemClicked(item: ItemModel) {
        sharedViewModel.navigateWithData(
            navigationData = NavigateWithData(
                id = R.id.action_mainScreen_to_itemDetailScreen,
                key = "itemId",
                data = item.id
            )
        )
    }

    override fun onSavedClicked(item: ItemModel, state: Boolean) {
        viewModel.removeFromSaved(itemId = item.id)
    }

    private fun checkUser() {
        if (viewModel.getUserId().isNotEmpty()) {
            viewModel.getItems()
            binding.savedItems.isVisible = true
            binding.emptyProfile.isVisible = false
        } else {
            binding.savedItems.isVisible = false
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

        viewModel.items.observe(viewLifecycleOwner) {
            binding.noData.isVisible = it.isEmpty()
            itemAdapter.submitList(it)
        }

        viewModel.result.observe(viewLifecycleOwner) {
            viewModel.getItems()
        }
    }
}
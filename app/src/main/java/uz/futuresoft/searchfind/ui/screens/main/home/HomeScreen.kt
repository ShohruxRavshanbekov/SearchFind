package uz.futuresoft.searchfind.ui.screens.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.futuresoft.searchfind.R
import uz.futuresoft.searchfind.common.SharedViewModel
import uz.futuresoft.searchfind.common.dialogs.LoadingDialog
import uz.futuresoft.searchfind.databinding.ScreenHomeBinding
import uz.futuresoft.searchfind.models.CategoryModel
import uz.futuresoft.searchfind.models.ItemModel
import uz.futuresoft.searchfind.ui.screens.main.home.adapters.category.CategoryAdapter
import uz.futuresoft.searchfind.ui.screens.main.home.adapters.category.CategoryAdapterListener
import uz.futuresoft.searchfind.ui.screens.main.home.adapters.item.ItemAdapter
import uz.futuresoft.searchfind.ui.screens.main.home.adapters.item.ItemAdapterListener
import uz.futuresoft.searchfind.utils.NavigateWithData
import uz.futuresoft.searchfind.utils.closeKeyboard
import uz.futuresoft.searchfind.utils.openKeyboard
import uz.futuresoft.searchfind.utils.showToast

@AndroidEntryPoint
class HomeScreen : Fragment(), CategoryAdapterListener, ItemAdapterListener {

    private val binding by lazy { ScreenHomeBinding.inflate(layoutInflater) }

    private val viewModel: HomeScreenViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val loadingDialog by lazy { LoadingDialog(context = requireContext()) }

    private val categoryAdapter by lazy { CategoryAdapter(listener = this) }
    private val itemAdapter by lazy { ItemAdapter(listener = this) }

    private var categoryId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackPressed()
        viewModel.getCategories()
        viewModel.getItems()
        updateUi()
        binding.categories.adapter = categoryAdapter
        binding.items.adapter = itemAdapter

        binding.searchText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) viewModel.getItemsByOrder(order = v.text.toString())
            true
        }

        binding.search.setOnClickListener {
            binding.toolBar.isVisible = false
            binding.categories.isVisible = false
            binding.searchBar.isVisible = true
            viewModel.getItems()
            sharedViewModel.setBottomNavigationState(isVisible = false)
            openKeyboard(editText = binding.searchText)
        }

        binding.notifications.setOnClickListener {
            sharedViewModel.navigate(id = R.id.action_mainScreen_to_notificationsScreen)
        }
    }

    override fun onCategoryClicked(category: CategoryModel) {
        categoryId = category.id
        if (category.name == "All") viewModel.getItems()
        else viewModel.getItemsByCategory(categoryId = categoryId)
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
        if (state) {
            viewModel.addToSaved(itemId = item.id)
        } else {
            viewModel.removeFromSaved(itemId = item.id)
        }
    }

    private fun updateUi() {
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) loadingDialog.show() else loadingDialog.dismiss()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showToast(message = getString(R.string.something_went_wrong))
        }

        viewModel.categories.observe(viewLifecycleOwner) {
            categoryAdapter.submitList(it)
        }

        viewModel.items.observe(viewLifecycleOwner) {
            binding.noData.isVisible = it.isEmpty()
            itemAdapter.submitList(it)
        }

        viewModel.result.observe(viewLifecycleOwner) {
            viewModel.getItems()
        }
    }

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            owner = viewLifecycleOwner,
            onBackPressedCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.searchBar.isVisible) {
                        binding.searchBar.isVisible = false
                        binding.toolBar.isVisible = true
                        binding.searchText.text?.clear()
                        binding.categories.isVisible = true
                        if (categoryId.isEmpty()) viewModel.getItems()
                        else viewModel.getItemsByCategory(categoryId = categoryId)
                        sharedViewModel.setBottomNavigationState(isVisible = true)
                        closeKeyboard(editText = binding.searchText)
                    } else {
                        requireActivity().finish()
                    }
                }
            }
        )
    }

    override fun onPause() {
        super.onPause()
        categoryId = ""
    }
}
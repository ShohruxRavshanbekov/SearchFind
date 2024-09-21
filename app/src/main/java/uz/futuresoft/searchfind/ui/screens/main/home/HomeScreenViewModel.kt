package uz.futuresoft.searchfind.ui.screens.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import uz.futuresoft.searchfind.local.storage.LocalStorage
import uz.futuresoft.searchfind.models.CategoryModel
import uz.futuresoft.searchfind.models.ItemModel
import uz.futuresoft.searchfind.useCase.category.GetCategoriesUseCase
import uz.futuresoft.searchfind.useCase.item.AddItemToSavedUseCase
import uz.futuresoft.searchfind.useCase.item.GetItemsByCategoryUseCase
import uz.futuresoft.searchfind.useCase.item.GetItemsByOrderUseCase
import uz.futuresoft.searchfind.useCase.item.GetItemsUseSase
import uz.futuresoft.searchfind.useCase.item.RemoveItemFromSavedUseCase
import uz.futuresoft.searchfind.utils.Constants
import uz.futuresoft.searchfind.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getItemsUseSase: GetItemsUseSase,
    private val getItemsByCategoryUseCase: GetItemsByCategoryUseCase,
    private val getItemsByOrderUseCase: GetItemsByOrderUseCase,
    private val addItemToSavedUseCase: AddItemToSavedUseCase,
    private val removeItemFromSavedUseCase: RemoveItemFromSavedUseCase,
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _categories = MutableLiveData<List<CategoryModel>>()
    val categories: LiveData<List<CategoryModel>> = _categories

    private val _items = MutableLiveData<List<ItemModel>>()
    val items: LiveData<List<ItemModel>> = _items

    private val _result = SingleLiveEvent<Unit>()
    val result: LiveData<Unit> = _result

    fun getCategories() {
        _loading.value = true

        viewModelScope.launch {
            getCategoriesUseCase.invoke().collect { result ->
                _loading.value = false

                result.onSuccess { _categories.value = it }
                result.onFailure { _error.value = it.message }
            }
        }
    }

    fun getItems() {
        _loading.value = true

        viewModelScope.launch {
            getItemsUseSase.invoke().collect { result ->
                _loading.value = false

                result.onSuccess { _items.value = it }
                result.onFailure { _error.value = it.message }
            }
        }
    }

    fun getItemsByCategory(categoryId: String) {
        _loading.value = true

        viewModelScope.launch {
            getItemsByCategoryUseCase.invoke(categoryId = categoryId).collect { result ->
                _loading.value = false

                result.onSuccess { _items.value = it }
                result.onFailure { _error.value = it.message }
            }
        }
    }

    fun getItemsByOrder(order: String) {
        _loading.value = true

        viewModelScope.launch {
            getItemsByOrderUseCase.invoke(order = order).collect { result ->
                _loading.value = false

                result.onSuccess { _items.value = it }
                result.onFailure { _error.value = it.message }
            }
        }
    }

    fun addToSaved(itemId: String) {
        _loading.value = true

        viewModelScope.launch {
            addItemToSavedUseCase.invoke(itemId = itemId).collect { result ->
                _loading.value = false

                result.onSuccess { _result.value = it }
                result.onFailure { _error.value = it.message }
            }
        }
    }

    fun removeFromSaved(itemId: String) {
        _loading.value = true

        viewModelScope.launch {
            removeItemFromSavedUseCase.invoke(itemId = itemId).collect { result ->
                _loading.value = false

                result.onSuccess { _result.value = it }
                result.onFailure { _error.value = it.message }
            }
        }
    }
}
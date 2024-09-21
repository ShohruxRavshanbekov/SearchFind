package uz.futuresoft.searchfind.ui.screens.main.saved

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.futuresoft.searchfind.local.storage.LocalStorage
import uz.futuresoft.searchfind.models.ItemModel
import uz.futuresoft.searchfind.models.UserModel
import uz.futuresoft.searchfind.useCase.item.AddItemToSavedUseCase
import uz.futuresoft.searchfind.useCase.item.GetSavedItemsUseCase
import uz.futuresoft.searchfind.useCase.item.RemoveItemFromSavedUseCase
import uz.futuresoft.searchfind.useCase.user.GetUserUseCase
import uz.futuresoft.searchfind.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class SavedScreenViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val getUserUseCase: GetUserUseCase,
    private val getSavedItemsUseCase: GetSavedItemsUseCase,
    private val removeItemFromSavedUseCase: RemoveItemFromSavedUseCase,
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _user = SingleLiveEvent<UserModel>()
    val user: LiveData<UserModel> = _user

    private val _items = MutableLiveData<List<ItemModel>>()
    val items: LiveData<List<ItemModel>> = _items

    private val _result = SingleLiveEvent<Unit>()
    val result: LiveData<Unit> = _result

    fun getUserId() = auth.currentUser?.uid ?: ""

    fun getUser(id: String) {
        _loading.value = true

        viewModelScope.launch {
            getUserUseCase.invoke(id = id).collect { result ->
                _loading.value = false

                result.onSuccess { user ->
                    _user.value = user
                }

                result.onFailure { exception ->
                    _error.value = exception.message
                }
            }
        }
    }

    fun getItems() {
        _loading.value = true

        viewModelScope.launch {
            getSavedItemsUseCase.invoke().collect { result ->
                _loading.value = false

                result.onSuccess { items ->
                    _items.value = items
                }

                result.onFailure { throwable ->
                    _error.value = throwable.message
                }
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
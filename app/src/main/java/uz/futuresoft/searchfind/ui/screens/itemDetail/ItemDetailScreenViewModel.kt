package uz.futuresoft.searchfind.ui.screens.itemDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.futuresoft.searchfind.models.CategoryModel
import uz.futuresoft.searchfind.models.ItemModel
import uz.futuresoft.searchfind.models.MessageModel
import uz.futuresoft.searchfind.models.UserModel
import uz.futuresoft.searchfind.useCase.item.AddItemToSavedUseCase
import uz.futuresoft.searchfind.useCase.item.GetItemByIdUseCase
import uz.futuresoft.searchfind.useCase.item.RemoveItemFromSavedUseCase
import uz.futuresoft.searchfind.useCase.message.AddMessageUseCase
import uz.futuresoft.searchfind.useCase.user.GetUserUseCase
import uz.futuresoft.searchfind.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class ItemDetailScreenViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val getUserUseCase: GetUserUseCase,
    private val getItemByIdUseCase: GetItemByIdUseCase,
    private val addItemToSavedUseCase: AddItemToSavedUseCase,
    private val removeItemFromSavedUseCase: RemoveItemFromSavedUseCase,
    private val addMessageUseCase: AddMessageUseCase,
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _item = MutableLiveData<ItemModel>()
    val item: LiveData<ItemModel> = _item

    private val _result = SingleLiveEvent<Unit>()
    val result: LiveData<Unit> = _result

    private val _addMessageResult = SingleLiveEvent<Unit>()
    val addMessageResult: LiveData<Unit> = _addMessageResult

    private val _user = SingleLiveEvent<UserModel>()
    val user: LiveData<UserModel> = _user

    fun getUserId() = auth.currentUser?.uid ?: ""

    fun getUser(id: String) {
        _loading.value = true

        viewModelScope.launch {
            getUserUseCase.invoke(id = id).collect { result ->
                _loading.value = false

                result.onSuccess { _user.value = it }
                result.onFailure { _error.value = it.message }
            }
        }
    }

    fun getItem(id: String) {
        _loading.value = true

        viewModelScope.launch {
            getItemByIdUseCase.invoke(id = id).collect { result ->
                _loading.value = false

                result.onSuccess { _item.value = it }
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

    fun sendMessage(message: MessageModel) {
        _loading.value = true

        viewModelScope.launch {
            addMessageUseCase.invoke(message = message).collect { result ->
                _loading.value = false

                result.onSuccess { _addMessageResult.value = it }
                result.onFailure { _error.value = it.message }
            }
        }
    }
}
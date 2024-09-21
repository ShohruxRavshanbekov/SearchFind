package uz.futuresoft.searchfind.ui.screens.main.add

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.futuresoft.searchfind.models.CategoryModel
import uz.futuresoft.searchfind.models.ItemModel
import uz.futuresoft.searchfind.models.UserModel
import uz.futuresoft.searchfind.useCase.category.GetCategoriesUseCase
import uz.futuresoft.searchfind.useCase.item.AddItemUseCase
import uz.futuresoft.searchfind.useCase.user.GetUserUseCase
import uz.futuresoft.searchfind.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class AddScreenViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val getUserUseCase: GetUserUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addItemUseCase: AddItemUseCase,
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _user = SingleLiveEvent<UserModel>()
    val user: LiveData<UserModel> = _user

    private val _categories = MutableLiveData<List<CategoryModel>>()
    val categories: LiveData<List<CategoryModel>> = _categories

    private val _addItemResult = SingleLiveEvent<Unit>()
    val addItemResult: LiveData<Unit> = _addItemResult

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

    fun getCategories() {
        _loading.value = true

        viewModelScope.launch {
            getCategoriesUseCase.invoke().collect { result ->
                _loading.value = false

                result.onSuccess { categories ->
                    _categories.value = categories
                }

                result.onFailure { throwable ->
                    _error.value = throwable.message
                }
            }
        }
    }

    fun addItem(picture: Uri?, item: ItemModel) {
        _loading.value = true

        viewModelScope.launch {
            addItemUseCase.invoke(picture = picture, item = item).collect { result ->
                _loading.value = false

                result.onSuccess { success ->
                    _addItemResult.value = success
                }

                result.onFailure { throwable ->
                    _error.value = throwable.message
                }
            }
        }
    }

}
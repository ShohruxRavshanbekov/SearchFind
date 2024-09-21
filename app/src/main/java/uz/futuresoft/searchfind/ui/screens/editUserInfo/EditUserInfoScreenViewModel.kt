package uz.futuresoft.searchfind.ui.screens.editUserInfo

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.futuresoft.searchfind.models.UserModel
import uz.futuresoft.searchfind.useCase.user.EditUserUseCase
import uz.futuresoft.searchfind.useCase.user.GetUserUseCase
import uz.futuresoft.searchfind.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class EditUserInfoScreenViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val getUserUseCase: GetUserUseCase,
    private val editUserUseCase: EditUserUseCase,
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel> = _user

    private val _editUserDataResult = SingleLiveEvent<Unit>()
    val editUserDataResult: LiveData<Unit> = _editUserDataResult

    private val _removePictureResult = SingleLiveEvent<Unit>()
    val removePictureResult: LiveData<Unit> = _removePictureResult

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

    fun editUser(picture: Uri?, user: UserModel) {
        _loading.value = true

        viewModelScope.launch {
            editUserUseCase.invoke(picture = picture, user = user).collect { result ->
                _loading.value = false

                result.onSuccess { _editUserDataResult.value = it }
                result.onFailure { _error.value = it.message }
            }
        }
    }

    fun removePicture(picture: Uri?, user: UserModel) {
        _loading.value = true

        viewModelScope.launch {
            editUserUseCase.invoke(picture = picture, user = user).collect { result ->
                _loading.value = false

                result.onSuccess { _removePictureResult.value = it }
                result.onFailure { _error.value = it.message }
            }
        }
    }
}
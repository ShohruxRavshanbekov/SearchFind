package uz.futuresoft.searchfind.ui.screens.auth.userData

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.futuresoft.searchfind.models.UserModel
import uz.futuresoft.searchfind.useCase.user.AddUserUseCase
import javax.inject.Inject

@HiltViewModel
class UserDataScreenViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase,
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _result = MutableLiveData<Unit>()
    val result: LiveData<Unit> = _result

    fun addUser(picture: Uri?, user: UserModel) {
        _loading.value = true

        viewModelScope.launch {
            addUserUseCase.invoke(picture = picture, user = user).collect { result ->
                _loading.value = false

                result.onSuccess {
                    _result.value = it
                }

                result.onFailure {
                    _error.value = it.message
                }
            }
        }
    }
}
package uz.futuresoft.searchfind.ui.screens.auth.checkSms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.futuresoft.searchfind.models.UserModel
import uz.futuresoft.searchfind.useCase.auth.SetCodeUseCase
import uz.futuresoft.searchfind.useCase.user.GetUserUseCase
import uz.futuresoft.searchfind.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class CheckSmsScreenViewModel @Inject constructor(
    private val setCodeUseCase: SetCodeUseCase,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _userId = SingleLiveEvent<String>()
    val userId: LiveData<String> = _userId

    private val _user = SingleLiveEvent<UserModel>()
    val user: LiveData<UserModel> = _user

    fun getUserId(verificationId: String, code: String) {
        _loading.value = true

        viewModelScope.launch {
            setCodeUseCase.invoke(verificationId = verificationId, code = code).collect { result ->
                _loading.value = false

                result.onSuccess { userId ->
                    _userId.value = userId
                }

                result.onFailure { exception ->
                    _error.value = exception.message
                }
            }
        }
    }

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
}
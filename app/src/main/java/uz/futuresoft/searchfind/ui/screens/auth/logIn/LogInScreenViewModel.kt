package uz.futuresoft.searchfind.ui.screens.auth.logIn

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.futuresoft.searchfind.useCase.auth.AuthenticateUseCase
import uz.futuresoft.searchfind.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class LogInScreenViewModel @Inject constructor(
    private val authenticateUseCase: AuthenticateUseCase,
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _verificationId = SingleLiveEvent<String>()
    val verificationId: LiveData<String> = _verificationId

    fun authenticate(activity: Activity, phoneNumber: String) {
        _loading.value = true

        viewModelScope.launch {
            authenticateUseCase.invoke(activity = activity, phoneNumber = phoneNumber)
                .collect { result ->
                    _loading.value = false

                    result.onSuccess { verificationId ->
                        _verificationId.value = verificationId
                    }

                    result.onFailure { exception ->
                        _error.value = exception.message
                    }
                }
        }
    }

}
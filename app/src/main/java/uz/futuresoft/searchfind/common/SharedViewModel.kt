package uz.futuresoft.searchfind.common

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.futuresoft.searchfind.utils.NavigateWithData
import uz.futuresoft.searchfind.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {

    private val _changeScreen = SingleLiveEvent<Int>()
    val changeScreen: SingleLiveEvent<Int> = _changeScreen

    private val _bottomNavigationState = SingleLiveEvent<Boolean>()
    val bottomNavigationState: SingleLiveEvent<Boolean> = _bottomNavigationState

    private val _navigate = SingleLiveEvent<Int>()
    val navigate: SingleLiveEvent<Int> = _navigate

    private val _backStack = SingleLiveEvent<Unit>()
    val backStack: SingleLiveEvent<Unit> = _backStack

    private val _logOut = SingleLiveEvent<Unit>()
    val logOut: SingleLiveEvent<Unit> = _logOut

    private val _navigateWithData = SingleLiveEvent<NavigateWithData<String>>()
    val navigateWithData: SingleLiveEvent<NavigateWithData<String>> = _navigateWithData

    fun setScreen(screenId: Int) {
        _changeScreen.value = screenId
    }

    fun setBottomNavigationState(isVisible: Boolean) {
        _bottomNavigationState.value = isVisible
    }

    fun navigate(id: Int) {
        _navigate.value = id
    }

    fun navigateWithData(navigationData: NavigateWithData<String>) {
        _navigateWithData.value = navigationData
    }

    fun popBackStack() {
        _backStack.value = Unit
    }

    fun logOut() {
        _logOut.value = Unit
    }
}
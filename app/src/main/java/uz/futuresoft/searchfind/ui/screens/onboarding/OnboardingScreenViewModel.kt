package uz.futuresoft.searchfind.ui.screens.onboarding

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.futuresoft.searchfind.local.storage.LocalStorage
import uz.futuresoft.searchfind.utils.Constants
import javax.inject.Inject

@HiltViewModel
class OnboardingScreenViewModel @Inject constructor(
    private val localStorage: LocalStorage,
) : ViewModel() {

    fun setLaunchState(state: Boolean) {
        localStorage.putBoolean(Constants.SharedPreferences.FIRST_LAUNCH, state)
    }
}
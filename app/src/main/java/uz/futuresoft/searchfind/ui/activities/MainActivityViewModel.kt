package uz.futuresoft.searchfind.ui.activities

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.futuresoft.searchfind.local.storage.LocalStorage
import uz.futuresoft.searchfind.utils.Constants
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val localStorage: LocalStorage,
) : ViewModel() {

    fun firstLaunch() = localStorage.getBoolean(key = Constants.SharedPreferences.FIRST_LAUNCH)

    fun getLanguage() = localStorage.getString(key = Constants.SharedPreferences.APP_LANGUAGE) ?: ""
}
package uz.futuresoft.searchfind.ui.screens.main.profile.dialogs.appLanguage

import androidx.lifecycle.ViewModel
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.futuresoft.searchfind.local.storage.LocalStorage
import uz.futuresoft.searchfind.utils.Constants
import javax.inject.Inject

@HiltViewModel
class AppLanguageBottomSheetDialogViewModel @Inject constructor() : ViewModel() {

    fun setLanguage(language: String) {
        Hawk.put(Constants.SharedPreferences.APP_LANGUAGE, language)
    }

    fun getLanguage() = Hawk.get(Constants.SharedPreferences.APP_LANGUAGE) ?: ""
}
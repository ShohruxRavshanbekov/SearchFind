package uz.futuresoft.searchfind.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import com.orhanobut.hawk.Hawk
import uz.futuresoft.searchfind.local.storage.LocalStorage
import uz.futuresoft.searchfind.local.storage.LocalStorageImpl
import java.util.Locale
import javax.inject.Inject

object LocaleManager {

    fun setLocale(context: Context): Context {
        Hawk.init(context).build()
        val language = getLanguagePref()
        return if (language == "")
            context
        else updateResources(context = context, language = language)
    }

    private fun getLanguagePref() =
        Hawk.get(Constants.SharedPreferences.APP_LANGUAGE) ?: ""

    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        val configuration: Configuration = context.resources.configuration
        configuration.setLocale(locale)
        context.createConfigurationContext(configuration)
        return context
    }
}
package uz.futuresoft.searchfind.local.storage

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.futuresoft.searchfind.models.UserModel
import javax.inject.Inject

class LocalStorageImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : LocalStorage {

    override fun putInt(key: String, data: Int) {
        sharedPreferences.edit().putInt(key, data).apply()
    }

    override fun getInt(key: String) = sharedPreferences.getInt(key, -1)

    override fun putLong(key: String, data: Long) {
        sharedPreferences.edit().putLong(key, data).apply()
    }

    override fun getLong(key: String) = sharedPreferences.getLong(key, -1L)

    override fun putFloat(key: String, data: Float) {
        sharedPreferences.edit().putFloat(key, data).apply()
    }

    override fun getFloat(key: String) = sharedPreferences.getFloat(key, -1.0F)

    override fun putString(key: String, data: String) {
        sharedPreferences.edit().putString(key, data).apply()
    }

    override fun getString(key: String) = sharedPreferences.getString(key, "") ?: ""

    override fun putBoolean(key: String, data: Boolean) {
        sharedPreferences.edit().putBoolean(key, data).apply()
    }

    override fun getBoolean(key: String) = sharedPreferences.getBoolean(key, false)

    override fun saveItem(key: String, itemId: String) {
        val savedItems = sharedPreferences.getStringSet(key, emptySet())?.toMutableList()
        savedItems?.add(itemId)
        sharedPreferences.edit().putStringSet(key, savedItems?.toSet()).apply()
    }

    override fun removeItem(key: String, itemId: String) {
        val savedItems = sharedPreferences.getStringSet(key, emptySet())?.toMutableList()
        savedItems?.remove(itemId)
        sharedPreferences.edit().putStringSet(key, savedItems?.toSet()).apply()
    }

    override fun getSavedItems(key: String) =
        sharedPreferences.getStringSet(key, emptySet())?.toList() ?: emptyList()

    override fun removeData(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    override fun clearStorage() {
        sharedPreferences.edit().clear().apply()
    }
}
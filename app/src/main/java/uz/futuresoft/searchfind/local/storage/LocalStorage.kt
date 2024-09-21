package uz.futuresoft.searchfind.local.storage

import uz.futuresoft.searchfind.models.UserModel

interface LocalStorage {
    fun putInt(key: String, data: Int)
    fun getInt(key: String): Int
    fun putLong(key: String, data: Long)
    fun getLong(key: String): Long
    fun putFloat(key: String, data: Float)
    fun getFloat(key: String): Float
    fun putString(key: String, data: String)
    fun getString(key: String): String?
    fun putBoolean(key: String, data: Boolean)
    fun getBoolean(key: String): Boolean
    fun saveItem(key: String, itemId: String)
    fun removeItem(key: String, itemId: String)
    fun getSavedItems(key: String): List<String>
    fun removeData(key: String)
    fun clearStorage()
}
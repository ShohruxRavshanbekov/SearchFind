package uz.futuresoft.searchfind.utils

import android.Manifest
import android.content.Context

object Constants {

    const val TAG = "AAAAA"

    const val LOST = "lost"
    const val FOUND = "found"

    object SharedPreferences {
        const val NAME = "app_local_storage"
        const val MODE = Context.MODE_PRIVATE
        const val FIRST_LAUNCH = "first_launch"
        const val SAVED_ITEMS = "saved_items"
        const val APP_LANGUAGE = "app_language"
    }

    object Firebase {
        const val COLLECTION_CATEGORY = "categories"
        const val COLLECTION_ITEMS = "items"
        const val COLLECTION_USERS = "users"
        const val COLLECTION_MESSAGES = "messages"
    }
}
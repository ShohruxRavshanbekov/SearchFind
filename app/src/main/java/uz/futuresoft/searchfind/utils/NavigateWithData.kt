package uz.futuresoft.searchfind.utils

data class NavigateWithData<T>(
    val id: Int,
    val key: String,
    val data: T,
)

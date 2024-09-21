package uz.futuresoft.searchfind.models

import com.google.firebase.firestore.DocumentId

data class CategoryModel(
    @DocumentId
    val id: String = "",
    val name: String = "",
)
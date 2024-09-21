package uz.futuresoft.searchfind.models

import com.google.firebase.firestore.DocumentId

data class MessageModel(
    @DocumentId
    val id: String = "",
    val itemOwnerId: String = "",
    val founder: String = "",
    val item: String = "",
    val contactNumber: String = "",
)

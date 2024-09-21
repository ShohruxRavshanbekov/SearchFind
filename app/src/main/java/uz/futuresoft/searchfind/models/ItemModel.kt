package uz.futuresoft.searchfind.models

import com.google.firebase.firestore.DocumentId
import java.io.Serializable

data class ItemModel(
    @DocumentId
    val id: String = "",
    val ownerId: String = "",
    val categoryId: String = "",
    val picture: String = "",
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val place: String = "",
    val status: String = "",
    val contactInfo: SocialMediaModel = SocialMediaModel(),
    var saved: Boolean = false,
) : Serializable

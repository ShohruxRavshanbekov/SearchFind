package uz.futuresoft.searchfind.models

import com.google.firebase.firestore.DocumentId
import java.io.Serializable

data class UserModel(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val surname: String = "",
    val picture: String = "",
    val phoneNumber: String = "",
) : Serializable

package uz.futuresoft.searchfind.models

import java.io.Serializable

data class SocialMediaModel(
    val phoneNumber: String = "",
    val email: String = "",
    val telegram: String = "",
    val instagram: String = "",
) : Serializable

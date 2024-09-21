package uz.futuresoft.searchfind.repository.auth

import android.app.Activity
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun authenticate(activity: Activity, phoneNumber: String): Flow<Result<String>>
    suspend fun setCode(verificationId: String, code: String): Flow<Result<String>>
}
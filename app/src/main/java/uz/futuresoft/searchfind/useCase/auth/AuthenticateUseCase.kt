package uz.futuresoft.searchfind.useCase.auth

import android.app.Activity
import kotlinx.coroutines.flow.Flow

interface AuthenticateUseCase {
    suspend fun invoke(activity: Activity, phoneNumber: String): Flow<Result<String>>
}
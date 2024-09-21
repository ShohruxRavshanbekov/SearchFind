package uz.futuresoft.searchfind.useCase.auth.impl

import android.app.Activity
import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.repository.auth.AuthRepository
import uz.futuresoft.searchfind.useCase.auth.AuthenticateUseCase
import javax.inject.Inject

class AuthenticateUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : AuthenticateUseCase {

    override suspend fun invoke(activity: Activity, phoneNumber: String): Flow<Result<String>> {
        return authRepository.authenticate(activity = activity, phoneNumber = phoneNumber)
    }
}
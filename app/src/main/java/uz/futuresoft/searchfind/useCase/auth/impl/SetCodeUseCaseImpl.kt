package uz.futuresoft.searchfind.useCase.auth.impl

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.repository.auth.AuthRepository
import uz.futuresoft.searchfind.useCase.auth.SetCodeUseCase
import javax.inject.Inject

class SetCodeUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : SetCodeUseCase {

    override suspend fun invoke(verificationId: String, code: String): Flow<Result<String>> {
        return authRepository.setCode(verificationId = verificationId, code = code)
    }
}
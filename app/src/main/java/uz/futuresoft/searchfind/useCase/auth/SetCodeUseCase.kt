package uz.futuresoft.searchfind.useCase.auth

import kotlinx.coroutines.flow.Flow

interface SetCodeUseCase {
    suspend fun invoke(verificationId: String, code: String): Flow<Result<String>>
}
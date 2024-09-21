package uz.futuresoft.searchfind.useCase.user

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.UserModel

interface GetUserUseCase {
    suspend fun invoke(id: String): Flow<Result<UserModel>>
}
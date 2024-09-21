package uz.futuresoft.searchfind.useCase.user.impl

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.UserModel
import uz.futuresoft.searchfind.repository.user.UserRepository
import uz.futuresoft.searchfind.useCase.user.GetUserUseCase
import javax.inject.Inject

class GetUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
) : GetUserUseCase {

    override suspend fun invoke(id: String): Flow<Result<UserModel>> {
        return userRepository.getUser(id = id)
    }
}
package uz.futuresoft.searchfind.useCase.user.impl

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.UserModel
import uz.futuresoft.searchfind.repository.user.UserRepository
import uz.futuresoft.searchfind.useCase.user.AddUserUseCase
import javax.inject.Inject

class AddUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
) : AddUserUseCase {

    override suspend fun invoke(picture: Uri?, user: UserModel): Flow<Result<Unit>> {
        return userRepository.addUser(picture = picture, user = user)
    }
}
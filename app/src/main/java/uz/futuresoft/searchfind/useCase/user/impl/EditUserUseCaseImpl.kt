package uz.futuresoft.searchfind.useCase.user.impl

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.UserModel
import uz.futuresoft.searchfind.repository.user.UserRepository
import uz.futuresoft.searchfind.useCase.user.EditUserUseCase
import javax.inject.Inject

class EditUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
) : EditUserUseCase {

    override suspend fun invoke(picture: Uri?, user: UserModel): Flow<Result<Unit>> {
        return userRepository.editUser(picture = picture, user = user)
    }
}
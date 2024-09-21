package uz.futuresoft.searchfind.useCase.user

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.UserModel

interface AddUserUseCase {
    suspend fun invoke(picture: Uri?, user: UserModel): Flow<Result<Unit>>
}
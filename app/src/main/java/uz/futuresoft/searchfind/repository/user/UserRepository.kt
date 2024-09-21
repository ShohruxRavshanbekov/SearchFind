package uz.futuresoft.searchfind.repository.user

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.UserModel

interface UserRepository {
    suspend fun addUser(picture: Uri?, user: UserModel): Flow<Result<Unit>>
    suspend fun getUser(id: String): Flow<Result<UserModel>>
    suspend fun editUser(picture: Uri?, user: UserModel): Flow<Result<Unit>>
    suspend fun deleteUser(id: String): Flow<Result<Unit>>
}
package uz.futuresoft.searchfind.useCase.message

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.MessageModel

interface DeleteMessageUseCase {
    suspend fun invoke(id: String): Flow<Result<Unit>>
}
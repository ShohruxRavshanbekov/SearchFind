package uz.futuresoft.searchfind.useCase.message

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.MessageModel

interface AddMessageUseCase {
    suspend fun invoke(message: MessageModel): Flow<Result<Unit>>
}
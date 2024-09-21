package uz.futuresoft.searchfind.useCase.message

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.MessageModel

interface GetMessagesUseCase {
    suspend fun invoke(userId: String): Flow<Result<List<MessageModel>>>
}
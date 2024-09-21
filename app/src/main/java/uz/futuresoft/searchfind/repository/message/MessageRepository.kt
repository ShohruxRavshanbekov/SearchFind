package uz.futuresoft.searchfind.repository.message

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.MessageModel

interface MessageRepository {
    suspend fun addMessage(message: MessageModel): Flow<Result<Unit>>
    suspend fun getMessages(userId: String): Flow<Result<List<MessageModel>>>
    suspend fun deleteMessage(id: String): Flow<Result<Unit>>
}
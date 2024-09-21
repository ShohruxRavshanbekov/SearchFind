package uz.futuresoft.searchfind.useCase.message.impl

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.MessageModel
import uz.futuresoft.searchfind.repository.message.MessageRepository
import uz.futuresoft.searchfind.useCase.message.GetMessagesUseCase
import javax.inject.Inject

class GetMessageUseCaseImpl @Inject constructor(
    private val messageRepository: MessageRepository,
) : GetMessagesUseCase {

    override suspend fun invoke(userId: String): Flow<Result<List<MessageModel>>> {
        return messageRepository.getMessages(userId = userId)
    }
}
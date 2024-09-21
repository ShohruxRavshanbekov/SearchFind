package uz.futuresoft.searchfind.useCase.message.impl

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.repository.message.MessageRepository
import uz.futuresoft.searchfind.useCase.message.DeleteMessageUseCase
import javax.inject.Inject

class DeleteMessageUseCaseImpl @Inject constructor(
    private val messageRepository: MessageRepository,
) : DeleteMessageUseCase {

    override suspend fun invoke(id: String): Flow<Result<Unit>> {
        return messageRepository.deleteMessage(id = id)
    }
}
package uz.futuresoft.searchfind.useCase.message.impl

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.MessageModel
import uz.futuresoft.searchfind.repository.message.MessageRepository
import uz.futuresoft.searchfind.useCase.message.AddMessageUseCase
import javax.inject.Inject

class AddMessageUseCaseImpl @Inject constructor(
    private val messageRepository: MessageRepository,
) : AddMessageUseCase {

    override suspend fun invoke(message: MessageModel): Flow<Result<Unit>> {
        return messageRepository.addMessage(message = message)
    }
}
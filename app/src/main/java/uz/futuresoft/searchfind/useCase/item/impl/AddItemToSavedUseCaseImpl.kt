package uz.futuresoft.searchfind.useCase.item.impl

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.ItemModel
import uz.futuresoft.searchfind.repository.item.ItemRepository
import uz.futuresoft.searchfind.useCase.item.AddItemToSavedUseCase
import javax.inject.Inject

class AddItemToSavedUseCaseImpl @Inject constructor(
    private val itemRepository: ItemRepository,
) : AddItemToSavedUseCase {

    override suspend fun invoke(itemId: String): Flow<Result<Unit>> {
        return itemRepository.addItemToSaved(itemId = itemId)
    }
}
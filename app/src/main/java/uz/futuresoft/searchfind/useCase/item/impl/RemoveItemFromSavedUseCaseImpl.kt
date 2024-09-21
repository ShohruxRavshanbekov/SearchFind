package uz.futuresoft.searchfind.useCase.item.impl

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.ItemModel
import uz.futuresoft.searchfind.repository.item.ItemRepository
import uz.futuresoft.searchfind.useCase.item.AddItemToSavedUseCase
import uz.futuresoft.searchfind.useCase.item.RemoveItemFromSavedUseCase
import javax.inject.Inject

class RemoveItemFromSavedUseCaseImpl @Inject constructor(
    private val itemRepository: ItemRepository,
) : RemoveItemFromSavedUseCase {

    override suspend fun invoke(itemId: String): Flow<Result<Unit>> {
        return itemRepository.removeItemFromSaved(itemId = itemId)
    }
}
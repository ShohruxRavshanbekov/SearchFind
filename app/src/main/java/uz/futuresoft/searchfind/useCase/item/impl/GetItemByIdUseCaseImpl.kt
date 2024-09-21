package uz.futuresoft.searchfind.useCase.item.impl

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.ItemModel
import uz.futuresoft.searchfind.repository.item.ItemRepository
import uz.futuresoft.searchfind.useCase.item.GetItemByIdUseCase
import javax.inject.Inject

class GetItemByIdUseCaseImpl @Inject constructor(
    private val itemRepository: ItemRepository,
) : GetItemByIdUseCase {

    override suspend fun invoke(id: String): Flow<Result<ItemModel>> {
        return itemRepository.getItemById(itemId = id)
    }
}
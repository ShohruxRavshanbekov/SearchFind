package uz.futuresoft.searchfind.useCase.item.impl

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.ItemModel
import uz.futuresoft.searchfind.repository.item.ItemRepository
import uz.futuresoft.searchfind.useCase.item.GetItemsUseSase
import javax.inject.Inject

class GetItemsUseCaseImpl @Inject constructor(
    private val itemRepository: ItemRepository,
) : GetItemsUseSase {

    override suspend fun invoke(): Flow<Result<List<ItemModel>>> {
        return itemRepository.getItems()
    }
}
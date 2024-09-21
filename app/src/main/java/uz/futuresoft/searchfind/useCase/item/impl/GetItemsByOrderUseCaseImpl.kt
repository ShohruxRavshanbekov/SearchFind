package uz.futuresoft.searchfind.useCase.item.impl

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.ItemModel
import uz.futuresoft.searchfind.repository.item.ItemRepository
import uz.futuresoft.searchfind.useCase.item.GetItemsByOrderUseCase
import javax.inject.Inject

class GetItemsByOrderUseCaseImpl @Inject constructor(
    private val itemRepository: ItemRepository,
) : GetItemsByOrderUseCase {

    override suspend fun invoke(order: String): Flow<Result<List<ItemModel>>> {
        return itemRepository.getItemsByOrder(order = order)
    }
}
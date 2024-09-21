package uz.futuresoft.searchfind.useCase.item.impl

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.ItemModel
import uz.futuresoft.searchfind.repository.item.ItemRepository
import uz.futuresoft.searchfind.useCase.item.GetItemsByCategoryUseCase
import javax.inject.Inject

class GetItemsByCategoryUseCaseImpl @Inject constructor(
    private val itemRepository: ItemRepository,
) : GetItemsByCategoryUseCase {

    override suspend fun invoke(categoryId: String): Flow<Result<List<ItemModel>>> {
        return itemRepository.getItemsByCategory(categoryId = categoryId)
    }
}
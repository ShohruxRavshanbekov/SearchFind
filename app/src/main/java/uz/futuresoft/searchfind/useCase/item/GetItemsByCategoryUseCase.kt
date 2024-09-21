package uz.futuresoft.searchfind.useCase.item

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.ItemModel

interface GetItemsByCategoryUseCase {
    suspend fun invoke(categoryId: String): Flow<Result<List<ItemModel>>>
}
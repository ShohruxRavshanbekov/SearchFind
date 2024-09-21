package uz.futuresoft.searchfind.useCase.item

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.ItemModel

interface GetItemsByOrderUseCase {
    suspend fun invoke(order: String): Flow<Result<List<ItemModel>>>
}
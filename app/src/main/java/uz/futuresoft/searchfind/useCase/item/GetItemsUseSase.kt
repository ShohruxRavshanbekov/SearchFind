package uz.futuresoft.searchfind.useCase.item

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.ItemModel

interface GetItemsUseSase {
    suspend fun invoke(): Flow<Result<List<ItemModel>>>
}
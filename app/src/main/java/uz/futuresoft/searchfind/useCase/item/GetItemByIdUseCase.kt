package uz.futuresoft.searchfind.useCase.item

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.ItemModel

interface GetItemByIdUseCase {
    suspend fun invoke(id: String): Flow<Result<ItemModel>>
}
package uz.futuresoft.searchfind.useCase.item

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.ItemModel

interface AddItemToSavedUseCase {
    suspend fun invoke(itemId:String): Flow<Result<Unit>>
}
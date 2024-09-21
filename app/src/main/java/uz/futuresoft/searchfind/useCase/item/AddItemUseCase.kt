package uz.futuresoft.searchfind.useCase.item

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.ItemModel

interface AddItemUseCase {
    suspend fun invoke(picture: Uri?, item: ItemModel): Flow<Result<Unit>>
}
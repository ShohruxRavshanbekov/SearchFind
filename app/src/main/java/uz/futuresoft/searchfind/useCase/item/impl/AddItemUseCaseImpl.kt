package uz.futuresoft.searchfind.useCase.item.impl

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.ItemModel
import uz.futuresoft.searchfind.repository.item.ItemRepository
import uz.futuresoft.searchfind.useCase.item.AddItemUseCase
import javax.inject.Inject

class AddItemUseCaseImpl @Inject constructor(
    private val itemRepository: ItemRepository,
) : AddItemUseCase {

    override suspend fun invoke(picture: Uri?, item: ItemModel): Flow<Result<Unit>> {
        return itemRepository.addItem(picture = picture, item = item)
    }
}
package uz.futuresoft.searchfind.repository.item

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.ItemModel

interface ItemRepository {
    suspend fun addItem(picture: Uri?, item: ItemModel): Flow<Result<Unit>>
    suspend fun addItemToSaved(itemId: String): Flow<Result<Unit>>
    suspend fun removeItemFromSaved(itemId: String): Flow<Result<Unit>>
    suspend fun getItems(): Flow<Result<List<ItemModel>>>
    suspend fun getSavedItems(): Flow<Result<List<ItemModel>>>
    suspend fun getItemsByCategory(categoryId: String): Flow<Result<List<ItemModel>>>
    suspend fun getItemsByUser(userId: String): Flow<Result<List<ItemModel>>>
    suspend fun getItemsByOrder(order: String): Flow<Result<List<ItemModel>>>
    suspend fun getItemById(itemId: String): Flow<Result<ItemModel>>
    suspend fun editItem(picture: Uri?, item: ItemModel): Flow<Result<Unit>>
    suspend fun deleteItem(id: String): Flow<Result<Unit>>
}
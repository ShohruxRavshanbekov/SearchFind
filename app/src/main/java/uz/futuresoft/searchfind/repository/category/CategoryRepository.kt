package uz.futuresoft.searchfind.repository.category

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.CategoryModel

interface CategoryRepository {
    suspend fun getCategories(): Flow<Result<List<CategoryModel>>>
}
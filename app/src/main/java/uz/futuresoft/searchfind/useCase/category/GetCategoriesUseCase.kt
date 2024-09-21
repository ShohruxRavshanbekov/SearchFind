package uz.futuresoft.searchfind.useCase.category

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.CategoryModel

interface GetCategoriesUseCase {
    suspend fun invoke(): Flow<Result<List<CategoryModel>>>
}
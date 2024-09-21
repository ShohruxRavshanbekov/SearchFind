package uz.futuresoft.searchfind.useCase.category

import kotlinx.coroutines.flow.Flow
import uz.futuresoft.searchfind.models.CategoryModel
import uz.futuresoft.searchfind.repository.category.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCaseImpl @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : GetCategoriesUseCase {

    override suspend fun invoke(): Flow<Result<List<CategoryModel>>> {
        return categoryRepository.getCategories()
    }
}
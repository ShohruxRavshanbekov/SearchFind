package uz.futuresoft.searchfind.di.binders

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.futuresoft.searchfind.repository.auth.AuthRepository
import uz.futuresoft.searchfind.repository.auth.AuthRepositoryImpl
import uz.futuresoft.searchfind.repository.category.CategoryRepository
import uz.futuresoft.searchfind.repository.category.CategoryRepositoryImpl
import uz.futuresoft.searchfind.repository.item.ItemRepository
import uz.futuresoft.searchfind.repository.item.ItemRepositoryImpl
import uz.futuresoft.searchfind.repository.message.MessageRepository
import uz.futuresoft.searchfind.repository.message.MessageRepositoryImpl
import uz.futuresoft.searchfind.repository.user.UserRepository
import uz.futuresoft.searchfind.repository.user.UserRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface BindRepository {

    @Binds
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    fun bindItemRepository(itemRepositoryImpl: ItemRepositoryImpl): ItemRepository

    @Binds
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    fun bindMessageRepository(messageRepositoryImpl: MessageRepositoryImpl): MessageRepository
}
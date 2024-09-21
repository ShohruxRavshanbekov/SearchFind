package uz.futuresoft.searchfind.di.binders

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.futuresoft.searchfind.useCase.auth.AuthenticateUseCase
import uz.futuresoft.searchfind.useCase.auth.SetCodeUseCase
import uz.futuresoft.searchfind.useCase.auth.impl.AuthenticateUseCaseImpl
import uz.futuresoft.searchfind.useCase.auth.impl.SetCodeUseCaseImpl
import uz.futuresoft.searchfind.useCase.category.GetCategoriesUseCase
import uz.futuresoft.searchfind.useCase.category.GetCategoriesUseCaseImpl
import uz.futuresoft.searchfind.useCase.item.AddItemToSavedUseCase
import uz.futuresoft.searchfind.useCase.item.AddItemUseCase
import uz.futuresoft.searchfind.useCase.item.GetItemByIdUseCase
import uz.futuresoft.searchfind.useCase.item.GetItemsByCategoryUseCase
import uz.futuresoft.searchfind.useCase.item.GetItemsByOrderUseCase
import uz.futuresoft.searchfind.useCase.item.impl.AddItemUseCaseImpl
import uz.futuresoft.searchfind.useCase.item.impl.GetItemsUseCaseImpl
import uz.futuresoft.searchfind.useCase.item.GetItemsUseSase
import uz.futuresoft.searchfind.useCase.item.GetSavedItemsUseCase
import uz.futuresoft.searchfind.useCase.item.RemoveItemFromSavedUseCase
import uz.futuresoft.searchfind.useCase.item.impl.AddItemToSavedUseCaseImpl
import uz.futuresoft.searchfind.useCase.item.impl.GetItemByIdUseCaseImpl
import uz.futuresoft.searchfind.useCase.item.impl.GetItemsByCategoryUseCaseImpl
import uz.futuresoft.searchfind.useCase.item.impl.GetItemsByOrderUseCaseImpl
import uz.futuresoft.searchfind.useCase.item.impl.GetSavedItemsUseCaseImpl
import uz.futuresoft.searchfind.useCase.item.impl.RemoveItemFromSavedUseCaseImpl
import uz.futuresoft.searchfind.useCase.message.AddMessageUseCase
import uz.futuresoft.searchfind.useCase.message.DeleteMessageUseCase
import uz.futuresoft.searchfind.useCase.message.GetMessagesUseCase
import uz.futuresoft.searchfind.useCase.message.impl.AddMessageUseCaseImpl
import uz.futuresoft.searchfind.useCase.message.impl.DeleteMessageUseCaseImpl
import uz.futuresoft.searchfind.useCase.message.impl.GetMessageUseCaseImpl
import uz.futuresoft.searchfind.useCase.user.AddUserUseCase
import uz.futuresoft.searchfind.useCase.user.EditUserUseCase
import uz.futuresoft.searchfind.useCase.user.GetUserUseCase
import uz.futuresoft.searchfind.useCase.user.impl.AddUserUseCaseImpl
import uz.futuresoft.searchfind.useCase.user.impl.EditUserUseCaseImpl
import uz.futuresoft.searchfind.useCase.user.impl.GetUserUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
interface BindUseCase {

    @Binds
    fun bindAuthenticateUseCase(authenticateUseCaseImpl: AuthenticateUseCaseImpl): AuthenticateUseCase

    @Binds
    fun bindSetCodeUseCase(setCodeUseCaseImpl: SetCodeUseCaseImpl): SetCodeUseCase

    @Binds
    fun bindGetCategoriesUseCase(getCategoriesUseCaseImpl: GetCategoriesUseCaseImpl): GetCategoriesUseCase

    @Binds
    fun bindAddItemUseCase(addItemUseCaseImpl: AddItemUseCaseImpl): AddItemUseCase

    @Binds
    fun bindGetItemsUseCase(getItemsUseCaseImpl: GetItemsUseCaseImpl): GetItemsUseSase

    @Binds
    fun bindGetItemByCategoryUseCase(getItemByCategoryUseCaseImpl: GetItemsByCategoryUseCaseImpl): GetItemsByCategoryUseCase

    @Binds
    fun bindGetItemByOrderUseCase(getItemsByOrderUseCaseImpl: GetItemsByOrderUseCaseImpl): GetItemsByOrderUseCase

    @Binds
    fun bindGetSavedItemsUseCase(getSavedItemsUseCaseImpl: GetSavedItemsUseCaseImpl): GetSavedItemsUseCase

    @Binds
    fun bindAddItemToSavedUseCase(addItemToSavedUseCaseImpl: AddItemToSavedUseCaseImpl): AddItemToSavedUseCase

    @Binds
    fun bindRemoveItemFromSavedUseCase(removeItemFromSavedUseCaseImpl: RemoveItemFromSavedUseCaseImpl): RemoveItemFromSavedUseCase

    @Binds
    fun bindGetItemByIdUseCase(getItemByIdUseCaseImpl: GetItemByIdUseCaseImpl): GetItemByIdUseCase

    @Binds
    fun bindAddUserUseCase(addUserUseCaseImpl: AddUserUseCaseImpl): AddUserUseCase

    @Binds
    fun bindGetUserUseCase(getUserUseCaseImpl: GetUserUseCaseImpl): GetUserUseCase

    @Binds
    fun bindEditUserUseCase(editUserUseCaseImpl: EditUserUseCaseImpl): EditUserUseCase

    @Binds
    fun bindAddMessageUseCase(addMessageUseCaseImpl: AddMessageUseCaseImpl): AddMessageUseCase

    @Binds
    fun bindGetMessagesUseCase(getMessagesUseCaseImpl: GetMessageUseCaseImpl): GetMessagesUseCase

    @Binds
    fun bindDeleteMessageUseCase(deleteMessageUseCaseImpl: DeleteMessageUseCaseImpl): DeleteMessageUseCase
}
package uz.futuresoft.searchfind.di.binders

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.futuresoft.searchfind.local.storage.LocalStorage
import uz.futuresoft.searchfind.local.storage.LocalStorageImpl

@Module
@InstallIn(SingletonComponent::class)
interface BindLocalStorage {

    @Binds
    fun bindLocalStorage(localStorageImpl: LocalStorageImpl): LocalStorage
}
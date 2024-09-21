package uz.futuresoft.searchfind.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.futuresoft.searchfind.utils.Constants

@Module
@InstallIn(SingletonComponent::class)
class LocalStorageModule {

    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
    ): SharedPreferences =
        context.getSharedPreferences(
            Constants.SharedPreferences.NAME,
            Constants.SharedPreferences.MODE
        )
}
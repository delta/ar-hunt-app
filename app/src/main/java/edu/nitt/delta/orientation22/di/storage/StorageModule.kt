package edu.nitt.delta.orientation22.di.storage

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Provides
    @Singleton
    fun provideSharedPrefHelper(app:Application):SharedPrefHelper = SharedPrefHelper(app)
}

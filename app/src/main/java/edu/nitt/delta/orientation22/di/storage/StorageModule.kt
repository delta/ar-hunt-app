package edu.nitt.delta.orientation22.di.storage

import android.app.Application
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    fun provideSharedPrefHelper(app:Application):SharedPrefHelper = SharedPrefHelper(app)
}

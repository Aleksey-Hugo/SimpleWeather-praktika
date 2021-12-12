package com.popov.simpleweather.di.module

import com.popov.simpleweather.data.DataRepository
import com.popov.simpleweather.data.local.LocalDataSourceImpl
import com.popov.simpleweather.data.remote.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun getRepository(local: LocalDataSourceImpl,remote:RemoteDataSourceImpl):DataRepository{
        return DataRepository(local,remote)
    }

}